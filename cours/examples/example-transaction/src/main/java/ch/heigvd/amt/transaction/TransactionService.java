package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import org.jboss.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;

@ApplicationScoped
public class TransactionService {

    private static final Logger LOG = Logger.getLogger(TransactionService.class.getName());

    @Inject
    EntityManager em;

    @Inject
    TransactionManager transactionManager;

    @Inject
    UserTransaction transaction;

    @Inject
    @Named("mysql")
    DataSource mysqlDataSource;

    @Inject
    @Named("maria")
    DataSource mariaDataSource;

    /**
     * Transactional can be used to control transaction boundaries on any CDI bean at the method level or at the class level to
     * ensure every method is transactional. That includes REST endpoints.
     * <p/>
     * You can control whether and how the transaction is started with parameters on @Transactional:
     * - REQUIRED (default): starts a transaction if none was started, stays with the existing one otherwise.
     * - REQUIRES_NEW: starts a transaction if none was started ; if an existing one was started, suspends it and starts a new
     * one for the boundary of that method.
     * - MANDATORY: fails if no transaction was started ; works within the existing transaction otherwise.
     * - SUPPORTS: if a transaction was started, joins it ; otherwise works with no transaction.
     * - NOT_SUPPORTED: if a transaction was started, suspends it and works with no transaction for the boundary of the method ;
     * otherwise works with no transaction.
     * - NEVER: if a transaction was started, raises an exception ; otherwise works with no transaction.
     */
    @Transactional
    public void outerTransaction() {
        logTransaction();
        em.persist(new PostgresEntity(1));
        innerTransaction();
    }

    @Transactional
    public void innerTransaction() {
        logTransaction();
        // The transaction created in methodA will be propagated to this method
        em.persist(new PostgresEntity(2));
    }

    public void logTransaction() {
        try {
            LOG.info("Transaction: " + transactionManager);
        } catch (Exception e) {
            LOG.error("Error while logging transaction", e);
        }
    }

    /**
     * In quarkus you must inject the UserTransaction object to manage transactions programmatically.
     * However, this is not recommended as it leads to bloated code and is error-prone.
     */
    public void programmaticTransaction() {
        try {
            transaction.begin();
            em.persist(new PostgresEntity(3));
            transaction.commit();
            LOG.info("Transaction committed");
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            LOG.error("Error while managing transaction programmatically", e);
        }
    }

    public void mysqlDataSourceTransaction() {
        try {
            Connection connection = mysqlDataSource.getConnection();
            connection.setAutoCommit(false);
            connection.createStatement().execute("INSERT INTO mysql VALUES (1)");
            connection.commit();
        } catch (Exception e) {
            LOG.error("Error while managing transaction programmatically", e);
        }
    }

    public void mariaDataSourceTransaction() {
        try {
            Connection connection = mariaDataSource.getConnection();
            connection.setAutoCommit(false);
            connection.createStatement().execute("INSERT INTO maria VALUES (1)");
            connection.commit();
        } catch (Exception e) {
            LOG.error("Error while managing transaction programmatically", e);
        }
    }

}

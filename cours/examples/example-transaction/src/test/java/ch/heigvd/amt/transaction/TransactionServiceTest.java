package ch.heigvd.amt.transaction;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TransactionServiceTest {

    private static final Logger LOG = Logger.getLogger(TransactionServiceTest.class.getName());

    @Inject
    EntityManager entityManager;

    @Inject
    @Named("mysql")
    DataSource mysqlDataSource;

    @Inject
    @Named("maria")
    DataSource mariaDataSource;

    @Inject
    TransactionService transactionService;

    @Test
    void nestedTransaction() {
        try {
            transactionService.outerTransaction();
        } catch (Exception e) {
            // Ignore
        }
        assertNotNull(entityManager.find(PostgresEntity.class, 1L));
    }

    @Test
    void programmaticTransaction() {
        try {
            transactionService.programmaticTransaction();
        } catch (Exception e) {
            // Ignore
        }
        assertNotNull(entityManager.find(PostgresEntity.class, 3L));
    }

    @Test
    void mysqlDataSourceTransaction() {
        try {
            transactionService.mysqlDataSourceTransaction();
        } catch (Exception e) {
            LOG.error("Error while executing transaction", e);
        }
        try (var connection = mysqlDataSource.getConnection()) {
            // connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
            // connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            // connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM mysql");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(1, count);
        } catch (SQLException exception) {
            LOG.error("Error while executing transaction", exception);
        }
    }

    @Test
    void mariaDataSourceTransaction() {
        try {
            transactionService.mariaDataSourceTransaction();
        } catch (Exception e) {
            LOG.error("Error while executing transaction", e);
        }
        try (var connection = mariaDataSource.getConnection()) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM maria");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(1, count);
        } catch (SQLException exception) {
            LOG.error("Error while executing transaction", exception);
        }
    }

}
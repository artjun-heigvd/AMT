package ch.heigvd.amt.transaction;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.SystemException;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class DistributedTransactionServiceTest {

    private static final Logger LOG = Logger.getLogger(DistributedTransactionServiceTest.class.getName());

    @Inject
    @Named("mysql")
    DataSource mysqlDataSource;

    @Inject
    @Named("maria")
    DataSource mariaDataSource;

    @Inject
    DistributedTransactionService xaTransactionService;

    @Test
    void xaTransaction() throws SQLException {
        try {
            xaTransactionService.xaTransaction();
        } catch (SystemException e) {
            LOG.error("Error while executing XA transaction", e);
        }
        try (var connection = mysqlDataSource.getConnection()) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM mysql");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(0, count);
        }
        try (var connection = mariaDataSource.getConnection()) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM maria");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(0, count);
        }
    }

}
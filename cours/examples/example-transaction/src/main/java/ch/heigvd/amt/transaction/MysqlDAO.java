package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

import javax.sql.DataSource;

@ApplicationScoped
public class MysqlDAO {

    private static final Logger LOG = Logger.getLogger(MysqlDAO.class.getName());

    @Inject
    @Named("mysql")
    DataSource mysqlDataSource;

    public void insert() {
        try (var connection = mysqlDataSource.getConnection()) {
            connection.createStatement().execute("INSERT INTO mysql VALUES (1)");
        } catch (Exception e) {
            LOG.error("Error while inserting data", e);
        }
    }
}

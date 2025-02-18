package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

import javax.sql.DataSource;

@ApplicationScoped
public class MariaDAO {

    private static final Logger LOG = Logger.getLogger(MariaDAO.class.getName());

    @Inject
    @Named("maria")
    private DataSource mariaDataSource;

    public void insert() {
        try (var connection = mariaDataSource.getConnection()) {
            connection.createStatement().execute("INSERT INTO maria VALUES (1)");
        } catch (Exception e) {
            LOG.error("Error while inserting data", e);
        }
    }
}

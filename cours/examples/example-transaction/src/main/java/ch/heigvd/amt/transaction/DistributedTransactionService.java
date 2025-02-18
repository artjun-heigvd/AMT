package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DistributedTransactionService {

    @Inject
    TransactionManager transactionManager;

    @Inject
    MariaDAO mariaDAO;

    @Inject
    MysqlDAO mysqlDAO;

    @Transactional
    public void xaTransaction() throws SystemException {
        try {

            mariaDAO.insert();
            mysqlDAO.insert();

            // Fail for some reason
            throw new RuntimeException("Transaction failed");

        } catch (Exception e) {
            transactionManager.setRollbackOnly();
        }
    }

}

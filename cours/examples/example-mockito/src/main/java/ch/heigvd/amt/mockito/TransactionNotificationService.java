package ch.heigvd.amt.mockito;

public interface TransactionNotificationService {
    void notifyTransaction(String userId, String message);
}
package ch.heigvd.amt.testing;

import ch.heigvd.amt.mockito.CryptoPricingService;
import ch.heigvd.amt.mockito.CryptoWallet;
import ch.heigvd.amt.mockito.TransactionNotificationService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CryptoWalletMockTest {

    @Test
    void testBuyCryptoVerifyNotification() {

        // Create mock objects
        CryptoPricingService pricingService = mock(CryptoPricingService.class);
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Initialize the wallet with mocked services
        CryptoWallet wallet = new CryptoWallet("user123", pricingService, notificationService);

        // Stub the pricing service
        when(pricingService.getPrice("BTC")).thenReturn(100000.0);
        when(pricingService.getPrice("USD")).thenReturn(1.0);

        // Simulate transferring USD to the wallet for the transaction
        wallet.transferCrypto("USD", 10000.0);

        // Buy cryptocurrency using the wallet
        wallet.buyCrypto("BTC", "USD", 0.1);

        // Verify that the notification service was called with the correct message
        verify(notificationService).notifyTransaction(
                "user123", "Buy 0.1 BTC completed."
        );
    }
}


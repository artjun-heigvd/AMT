package ch.heigvd.amt.testing;

import ch.heigvd.amt.mockito.CryptoPricingService;
import ch.heigvd.amt.mockito.CryptoWallet;
import ch.heigvd.amt.mockito.TransactionNotificationService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CryptoWalletSpyTest {

    @Test
    void testCalculateTotalValueUsingSpy() {

        // Create a real implementation of the pricing service
        CryptoPricingService realPricingService = new CryptoPricingService() {
            @Override
            public double getPrice(String cryptoSymbol) {
                switch (cryptoSymbol) {
                    case "BTC": return 100000.0;
                    case "ETH": return 8000.0;
                    default: return 0.0;
                }
            }
        };

        // Create a spy from the real implementation
        CryptoPricingService pricingServiceSpy = spy(realPricingService);

        // Mock the notification service
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Initialize the wallet with the spied pricing service
        CryptoWallet wallet = new CryptoWallet("user123", pricingServiceSpy, notificationService);

        // Override specific behavior of the spy
        doReturn(80000.0).when(pricingServiceSpy).getPrice("BTC");

        // Simulate transferring cryptocurrencies into the wallet
        wallet.transferCrypto("BTC", 0.1);  // Add 0.1 BTC
        wallet.transferCrypto("ETH", 2.0); // Add 2 ETH

        // Calculate the total value of the wallet
        double totalValue = wallet.calculateTotalValue();

        // Assert the expected value (0.1 BTC * $60,000 + 2 ETH * $4,000)
        assertEquals(8000.0 + 16000.0, totalValue, 0.01);

        // Verify that the spy was used
        verify(pricingServiceSpy).getPrice("BTC");
        verify(pricingServiceSpy).getPrice("ETH");
    }
}

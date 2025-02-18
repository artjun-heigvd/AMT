package ch.heigvd.amt.testing;

import ch.heigvd.amt.mockito.CryptoPricingService;
import ch.heigvd.amt.mockito.CryptoWallet;
import ch.heigvd.amt.mockito.TransactionNotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CryptoWalletStubTest {

    @Test
    void testCalculateTotalValueUsingStub() {

        // Create mock objects
        CryptoPricingService pricingService = mock(CryptoPricingService.class);
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Initialize the wallet with the mocked services
        CryptoWallet wallet = new CryptoWallet("user123", pricingService, notificationService);

        // Stub the pricing service to return specific prices for cryptocurrencies
        when(pricingService.getPrice("BTC")).thenReturn(100000.0);
        when(pricingService.getPrice("ETH")).thenReturn(8000.0);

        // Simulate transferring cryptocurrencies into the wallet
        wallet.transferCrypto("BTC", 0.1);
        wallet.transferCrypto("ETH", 2.0);

        // Calculate the total value of the wallet
        double totalValue = wallet.calculateTotalValue();

        // Assert the expected value
        assertEquals(10000.0 + 16000.0, totalValue, 0.01);
    }

    @Test
    void testWithArgumentMatchers() {
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Interact with the mock
        notificationService.notifyTransaction("user123", "Transaction completed");
        notificationService.notifyTransaction("user456", "Transaction completed");

        // Use argument matchers for verification
        verify(notificationService).notifyTransaction(eq("user123"), contains("completed"));
        verify(notificationService).notifyTransaction(eq("user456"), contains("completed"));
    }

    @Test
    void testVerificationModes() {
        CryptoPricingService pricingService = mock(CryptoPricingService.class);

        when(pricingService.getPrice("BTC")).thenReturn(100000.0);

        // Call the mock multiple times
        pricingService.getPrice("BTC");
        pricingService.getPrice("BTC");

        // Verify the exact number of invocations
        verify(pricingService, times(2)).getPrice("BTC");

        // Verify no interaction for another cryptocurrency
        verify(pricingService, never()).getPrice("ETH");
    }

    @Test
    void testInOrderVerification() {
        CryptoPricingService pricingService = mock(CryptoPricingService.class);
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Call methods in order
        pricingService.getPrice("BTC");
        notificationService.notifyTransaction("user123", "Price fetched");

        // Verify order of interactions
        InOrder inOrder = inOrder(pricingService, notificationService);
        inOrder.verify(pricingService).getPrice("BTC");
        inOrder.verify(notificationService).notifyTransaction("user123", "Price fetched");
    }

    @Test
    void testArgumentCaptor() {
        TransactionNotificationService notificationService = mock(TransactionNotificationService.class);

        // Interact with the mock
        notificationService.notifyTransaction("user123", "Transaction completed");

        // Capture the arguments
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(notificationService).notifyTransaction(eq("user123"), captor.capture());

        // Assert the captured argument
        assertEquals("Transaction completed", captor.getValue());
    }

    @Test
    void testResetMocks() {
        CryptoPricingService pricingService = mock(CryptoPricingService.class);

        // Stub and interact with the mock
        when(pricingService.getPrice("BTC")).thenReturn(50000.0);
        pricingService.getPrice("BTC");

        // Reset the mock
        reset(pricingService);

        // Verify that all stubs and interactions are cleared
        verifyNoInteractions(pricingService);
    }

}

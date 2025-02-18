package ch.heigvd.amt.mockito;

import java.util.HashMap;
import java.util.Map;

public class CryptoWallet {

    private final String userId;
    private final Map<String, Double> wallet = new HashMap<>();
    private final CryptoPricingService pricingService;
    private final TransactionNotificationService notificationService;

    public CryptoWallet(String userId, CryptoPricingService pricingService, TransactionNotificationService notificationService) {
        this.userId = userId;
        this.pricingService = pricingService;
        this.notificationService = notificationService;
    }

    public void transferCrypto(String cryptoSymbol, double amount) {
        validateAmount(amount);

        // Update wallet balance for the given cryptocurrency
        wallet.put(cryptoSymbol, wallet.getOrDefault(cryptoSymbol, 0.0) + amount);

        // Notify the user of the transfer
        notificationService.notifyTransaction(userId, "Received " + amount + " " + cryptoSymbol + " in transfer.");
    }

    public void buyCrypto(String cryptoSymbol, String withCryptoSymbol, double amount) {
        validateAmount(amount);

        // Get prices for both cryptocurrencies
        double cryptoPrice = getPrice(cryptoSymbol);
        double baseCurrencyPrice = getPrice(withCryptoSymbol);

        // Calculate the amount needed in the base currency
        double requiredBaseCurrencyAmount = (cryptoPrice * amount) / baseCurrencyPrice;

        // Check if the user has enough of the base currency
        if (wallet.getOrDefault(withCryptoSymbol, 0.0) < requiredBaseCurrencyAmount) {
            throw new IllegalArgumentException("Insufficient balance for " + withCryptoSymbol);
        }

        // Deduct the required base currency and add the purchased cryptocurrency
        wallet.put(withCryptoSymbol, wallet.get(withCryptoSymbol) - requiredBaseCurrencyAmount);
        wallet.put(cryptoSymbol, wallet.getOrDefault(cryptoSymbol, 0.0) + amount);

        // Notify the user
        notifyUser(cryptoSymbol, "Buy", amount);
    }

    public void sellCrypto(String cryptoSymbol, String toCryptoSymbol, double amount) {
        validateAmount(amount);

        // Get prices for both cryptocurrencies
        double cryptoPrice = getPrice(cryptoSymbol);
        double baseCurrencyPrice = getPrice(toCryptoSymbol);

        // Calculate the equivalent amount in the target currency
        double equivalentTargetCurrencyAmount = (cryptoPrice * amount) * baseCurrencyPrice;

        // Check if the user has enough of the cryptocurrency to sell
        if (wallet.getOrDefault(cryptoSymbol, 0.0) < amount) {
            throw new IllegalArgumentException("Insufficient balance for " + cryptoSymbol);
        }

        // Deduct the sold cryptocurrency and add the target currency
        wallet.put(cryptoSymbol, wallet.get(cryptoSymbol) - amount);
        wallet.put(toCryptoSymbol, wallet.getOrDefault(toCryptoSymbol, 0.0) + equivalentTargetCurrencyAmount);

        // Notify the user
        notifyUser(cryptoSymbol, "Sell", amount);
    }

    public double calculateTotalValue() {
        return wallet.entrySet().stream()
                .mapToDouble(entry -> getPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    private void notifyUser(String cryptoSymbol, String transactionType, double amount) {
        notificationService.notifyTransaction(
                userId,
                transactionType + " " + amount + " " + cryptoSymbol + " completed."
        );
    }

    private double getPrice(String cryptoSymbol) {
        double price = pricingService.getPrice(cryptoSymbol);
        if (price <= 0) {
            throw new IllegalArgumentException("Invalid price for " + cryptoSymbol + ": " + price);
        }
        return price;
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
}

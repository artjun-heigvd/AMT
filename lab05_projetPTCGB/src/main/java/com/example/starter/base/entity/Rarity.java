package com.example.starter.base.entity;

/**
 * Enum for the rarity of a card.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public enum Rarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    RARE_EX("Rare EX"),
    FULL_ART("Full Art"),
    FULL_ART_EX_SUPPORT("Full Art EX/Support"),
    IMMERSIVE("Immersive"),
    GOLD_CROWN("Gold Crown"),
    PROMO("Promo");

    private final String value;

    /**
     * Constructor for the Rarity enum.
     *
     * @param value the value of the rarity
     */
    Rarity(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the rarity.
     *
     * @return the value of the rarity
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converts a string to a Rarity enum.
     *
     * @param value the string to convert
     * @return the Rarity enum
     */
    public static Rarity fromString(String value) {
        return switch (value) {
            case "Common" -> COMMON;
            case "Uncommon" -> UNCOMMON;
            case "Rare" -> RARE;
            case "Rare EX" -> RARE_EX;
            case "Full Art" -> FULL_ART;
            case "Full Art EX/Support" -> FULL_ART_EX_SUPPORT;
            case "Immersive" -> IMMERSIVE;
            case "Gold Crown" -> GOLD_CROWN;
            case "Promo" -> PROMO;
            default -> throw new IllegalArgumentException("Invalid rarity: " + value);
        };
    }
}

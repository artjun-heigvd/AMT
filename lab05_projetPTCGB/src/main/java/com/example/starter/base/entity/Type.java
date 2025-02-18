package com.example.starter.base.entity;

/**
 * Enum for the type of a card.
 * The types are based on the Pokemon TCG types.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public enum Type {
    GRASS("Grass"),
    FIRE("Fire"),
    WATER("Water"),
    LIGHTNING("Lightning"),
    PSYCHIC("Psychic"),
    FIGHTING("Fighting"),
    DARKNESS("Darkness"),
    METAL("Metal"),
    DRAGON("Dragon"),
    COLORLESS("Colorless"),
    ITEM("Item"),
    SUPPORTER("Supporter"),
    POKEMON("Pokemon");

    private final String value;

    /**
     * Constructor for the PokemonType enum.
     *
     * @param value the value of the type
     */
    Type(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the type.
     *
     * @return the value of the type
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converts a string to a PokemonType enum.
     *
     * @param value the string to convert
     * @return the PokemonType enum
     */
    public static Type fromString(String value) {
        // First try to match the exact enum name
        try {
            return Type.valueOf(value);
        } catch (IllegalArgumentException e) {
            // If that fails, try to match the display value
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid type: " + value);
        }
    }
}

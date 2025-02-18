package com.example.starter.base.entity;

/**
 * Enum for the stage of a card.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public enum Stage {
    BASIC("Basic"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2"),
    NA("N/A");

    private final String value;

    /**
     * Constructor for the Stage enum.
     *
     * @param value the value of the stage
     */
    Stage(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the stage.
     *
     * @return the value of the stage
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converts a string to a Stage enum.
     *
     * @param value the string to convert
     * @return the Stage enum
     */
    public static Stage fromString(String value) {
        return switch (value) {
            case "Basic" -> BASIC;
            case "Stage 1" -> STAGE_1;
            case "Stage 2" -> STAGE_2;
            case "N/A" -> NA;
            default -> throw new IllegalArgumentException("Invalid stage: " + value);
        };
    }
}

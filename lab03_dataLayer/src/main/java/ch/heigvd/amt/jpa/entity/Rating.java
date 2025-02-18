package ch.heigvd.amt.jpa.entity;

/**
 * This enum represents the MPAA rating of a film.
 * The different ratings are:
 * - G: General audiences
 * - PG: Parental guidance suggested
 * - PG-13: Parents strongly cautioned
 * - R: Restricted
 * - NC-17: No one 17 and under admitted
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String value;

    /**
     * Constructor for the Rating enum.
     *
     * @param value the value of the rating
     */
    Rating(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the rating.
     *
     * @return the value of the rating
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converts a string to a Rating enum.
     *
     * @param value the string to convert
     * @return the Rating enum
     */
    public static Rating fromString(String value) {

        return switch (value) {
            case "G" -> G;
            case "PG" -> PG;
            case "PG-13" -> PG13;
            case "R" -> R;
            case "NC-17" -> NC17;
            default -> throw new IllegalArgumentException("Invalid rating: " + value);
        };
    }

}

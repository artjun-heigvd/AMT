package ch.heigvd.amt.jpa.converter;

import ch.heigvd.amt.jpa.entity.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for converting between {@link Rating} enums and their String representations in the database.
 * This converter is applied automatically to all Rating attributes in entities.
 *
 * @author Eva Ray
 * @author Rachel Trachida
 * @author Arthur Junod
 * @author Edwin Häffner
 */
@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    /**
     * Converts a {@link Rating} attribute to its corresponding String representation
     * for storage in the database.
     *
     * @param attribute the Rating attribute to convert
     * @return the name of the Rating as a String, or null if the attribute is null
     */
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    /**
     * Converts a String representation of a {@link Rating} back to its corresponding Rating enum.
     *
     * @param dbData the String representation of the Rating stored in the database
     * @return the corresponding Rating enum, or null if dbData is null
     */
    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rating.fromString(dbData);
    }
}

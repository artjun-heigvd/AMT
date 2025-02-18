package com.example.starter.base.converter;

import com.example.starter.base.entity.Rarity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for converting between {@link Rarity} enums and their String representations in the database.
 * This converter is applied automatically to all Rarity attributes in entities.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Converter(autoApply = true)
public class RarityConverter implements AttributeConverter<Rarity, String> {

    /**
     * Converts a {@link Rarity} attribute to its corresponding String representation
     * for storage in the database.
     *
     * @param attribute the Rarity attribute to convert
     * @return the name of the Rarity as a String, or null if the attribute is null
     */
    @Override
    public String convertToDatabaseColumn(Rarity attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    /**
     * Converts a String representation of a {@link Rarity} back to its corresponding Rarity enum.
     *
     * @param dbData the String representation of the Rarity stored in the database
     * @return the corresponding Rarity enum, or null if dbData is null
     */
    @Override
    public Rarity convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rarity.fromString(dbData);
    }
}

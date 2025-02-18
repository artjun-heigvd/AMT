package com.example.starter.base.converter;

import com.example.starter.base.entity.Type;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for converting between {@link Type} enums and their String representations in the database.
 * This converter is applied automatically to all PokemonType attributes in entities.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String> {

    /**
     * Converts a {@link Type} attribute to its corresponding String representation
     * for storage in the database.
     *
     * @param attribute the PokemonType attribute to convert
     * @return the name of the PokemonType as a String, or null if the attribute is null
     */
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    /**
     * Converts a String representation of a {@link Type} back to its corresponding PokemonType enum.
     *
     * @param dbData the String representation of the PokemonType stored in the database
     * @return the corresponding PokemonType enum, or null if dbData is null
     */
    @Override
    public Type convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Type.fromString(dbData);
    }
}

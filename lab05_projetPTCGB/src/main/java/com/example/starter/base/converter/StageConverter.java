package com.example.starter.base.converter;

import com.example.starter.base.entity.Stage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for converting between {@link Stage} enums and their String representations in the database.
 * This converter is applied automatically to all Stage attributes in entities.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Converter(autoApply = true)
public class StageConverter implements AttributeConverter<Stage, String> {

    /**
     * Converts a {@link Stage} attribute to its corresponding String representation
     * for storage in the database.
     *
     * @param attribute the Stage attribute to convert
     * @return the name of the Stage as a String, or null if the attribute is null
     */
    @Override
    public String convertToDatabaseColumn(Stage attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    /**
     * Converts a String representation of a {@link Stage} back to its corresponding Stage enum.
     *
     * @param dbData the String representation of the Stage stored in the database
     * @return the corresponding Stage enum, or null if dbData is null
     */
    @Override
    public Stage convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stage.fromString(dbData);
    }
}

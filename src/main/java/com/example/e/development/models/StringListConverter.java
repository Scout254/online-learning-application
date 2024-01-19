package com.example.e.development.models;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;


@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute != null ? String.join(DELIMITER, attribute) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.asList(dbData.split(DELIMITER)) : null;
    }
}

package com.example.demo.domain.converter;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        if (attribute != null) {
            return attribute.format(FORMATTER);
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return LocalDate.parse(dbData, FORMATTER);
        }
        return null;
    }
}

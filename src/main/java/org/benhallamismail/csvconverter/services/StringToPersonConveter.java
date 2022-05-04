package org.benhallamismail.csvconverter.services;

import org.benhallamismail.csvconverter.exceptions.ConversionException;
import org.benhallamismail.csvconverter.models.Person;
import org.springframework.core.convert.converter.Converter;

public class StringToPersonConveter implements Converter<String, Person> {
    @Override
    public Person convert(String source) {
        try {
            String[] values = source.split(",");
            return new Person(values[0].trim(), Integer.parseInt(values[1].trim()));
        } catch (RuntimeException e) {
            throw new ConversionException(source, Person.class, e);
        }
    }
}

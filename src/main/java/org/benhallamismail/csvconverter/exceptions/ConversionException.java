package org.benhallamismail.csvconverter.exceptions;

public class ConversionException extends org.springframework.core.convert.ConversionException {

    public ConversionException(String source, Class<?> clazz, Throwable throwable) {
        super(String.format("Error while trying to convert '%s' to '%s' class instance", source,  clazz.getName()), throwable);
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String source, Class<?> clazz) {
        this(source, clazz, null);
    }
}

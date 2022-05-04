package org.benhallamismail.csvconverter;

import org.benhallamismail.csvconverter.models.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class StringToPersonConveterTests {

    @Autowired
    private ConversionService conversionService;

    @Value("classpath:persons.csv")
    private Resource personResource;

    @Test
    void testConverter_isOk() {
        var person = conversionService.convert("Ismail, 23", Person.class);
        assertThat(person).isNotNull().isEqualTo(new Person("Ismail", 23));
    }

    @Test
    void testConverter_invalidAge() {
        assertThrows(ConversionException.class, () -> {
            conversionService.convert("Ismail, BENHALLAM", Person.class);
        });
    }

    @Test
    void testConverter_invalidSeparator() {
        assertThrows(ConversionException.class, () -> {
            conversionService.convert("ismail BENHALLAM", Person.class);
        });
    }

    @Test
    void testConverterFromCsv_isOk() {
        try (var reader = new BufferedReader(new InputStreamReader(personResource.getInputStream()))) {
            String line;
            int correctRows = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    conversionService.convert(line, Person.class);
                    correctRows++;
                } catch (RuntimeException ignored) {
                }
            }
            assertThat(correctRows).isEqualTo(4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

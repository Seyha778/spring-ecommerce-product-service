package com.apextech.productservice.utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class NumberGeneratorUtil {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private NumberGeneratorUtil() {
    }

    public static String generate(String prefix, Long sequence) {

        String date = LocalDate.now().format(DATE_FORMAT);

        return "%s-%s-%06d".formatted(
                prefix,
                date,
                sequence
        );
    }
}
package com.mkurczuk.crypto.services.report;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class LocalDateParser {

    LocalDate parse(String locaDateTimeString) {
        return LocalDate.parse(locaDateTimeString);
    }
}

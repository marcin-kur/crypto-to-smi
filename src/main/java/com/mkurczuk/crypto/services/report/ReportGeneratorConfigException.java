package com.mkurczuk.crypto.services.report;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ReportGeneratorConfigException extends RuntimeException {
    ReportGeneratorConfigException(String message) {
        super(message);
    }
}

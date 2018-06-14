package com.mkurczuk.crypto.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RestException extends RuntimeException {
    RestException(HttpStatus statusCode, String body) {
        super(statusCode.getReasonPhrase() + ": " + body);
    }

    public RestException(String message) {
        super(message);
    }
}

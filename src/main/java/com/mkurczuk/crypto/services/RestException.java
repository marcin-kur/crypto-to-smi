package com.mkurczuk.crypto.services;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    public RestException(HttpStatus statusCode, String body) {
        super(statusCode.getReasonPhrase() + ": " + body);
    }
}

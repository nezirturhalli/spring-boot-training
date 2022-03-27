package com.example.mongorestapi.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String i18nId;
    private final String debugId;

    public UserNotFoundException(String message, String i18nId, String debugId) {
        super(message);
        this.i18nId = i18nId;
        this.debugId = debugId;
    }
}
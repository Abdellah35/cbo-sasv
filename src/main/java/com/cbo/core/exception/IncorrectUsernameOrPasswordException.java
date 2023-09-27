package com.cbo.core.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {
    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }
}

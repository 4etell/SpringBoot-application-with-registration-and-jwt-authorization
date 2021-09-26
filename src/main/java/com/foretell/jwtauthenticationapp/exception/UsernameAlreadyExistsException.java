package com.foretell.jwtauthenticationapp.exception;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(final String message) {
        super(message);
    }
}

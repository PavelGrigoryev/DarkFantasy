package com.grigoryev.heroes.exception;

public class HikariConnectionPoolException extends RuntimeException {

    public HikariConnectionPoolException(String message) {
        super(message);
    }

}

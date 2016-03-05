package com.shockn745.tictactoe;

public class InvalidPlayerException extends RuntimeException {

    public InvalidPlayerException() {
    }

    public InvalidPlayerException(String message) {
        super(message);
    }

    public InvalidPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}

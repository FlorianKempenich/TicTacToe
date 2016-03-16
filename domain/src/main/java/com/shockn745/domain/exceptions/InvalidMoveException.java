package com.shockn745.domain.exceptions;

public class InvalidMoveException extends IllegalStateException {

    public InvalidMoveException() {
    }

    public InvalidMoveException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}

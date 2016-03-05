package com.shockn745.tictactoe;

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

package com.shockn745.application.driving.dto;

/**
 * @author Kempenich Florian
 */
public class BoardCoordinates {

    public final int x;
    public final int y;

    public BoardCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static BoardCoordinates noCoordinates() {
        return new BoardCoordinates(-1, -1);
    }

    @Override
    public String toString() {
        if (this.equals(BoardCoordinates.noCoordinates())) {
            return "No coordinates";
        } else {
            return "(" + x + ", " + y + ")";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardCoordinates that = (BoardCoordinates) o;

        if (x != that.x) {
            return false;
        }
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

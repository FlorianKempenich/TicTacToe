package com.shockn745.domain;

import com.shockn745.domain.exceptions.IllegalCoordinatesException;

/**
 * @author Kempenich Florian
 */
public class BoardCoordinatesModel {

    public final int x;
    public final int y;

    private BoardCoordinatesModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static BoardCoordinatesModel noCoordinates() {
        return new BoardCoordinatesModel(-1, -1);
    }

    public static BoardCoordinatesModel fromCoordinates(int x, int y) {
        checkForIllegalCoordinates(x, y);
        return new BoardCoordinatesModel(x, y);
    }

    private static void checkForIllegalCoordinates(int x, int y) {
        checkForIllegalValue(x);
        checkForIllegalValue(y);
    }

    private static void checkForIllegalValue(int coord) {
        if (coord < 0 || coord >= 3) {
            throw new IllegalCoordinatesException("Out of bounds coordinates");
        }
    }

    @Override
    public String toString() {
        if (this.equals(BoardCoordinatesModel.noCoordinates())) {
            return "No coordinates";
        } else {
            return "(" + x + ", " + y + ")";
        }
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardCoordinatesModel that = (BoardCoordinatesModel) o;

        if (x != that.x) {
            return false;
        }
        return y == that.y;

    }

}

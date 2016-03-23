package com.shockn745.domain;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.domain.exceptions.IllegalCoordinatesException;

/**
 * @author Kempenich Florian
 */
public class BoardCoordinatesModel {

    public final int x;
    public final int y;

    public BoardCoordinatesModel(BoardCoordinates coordinates) {
        this(coordinates.x, coordinates.y);
        checkForIllegalCoordinates();
    }

    public static BoardCoordinatesModel noCoordinates() {
        return new BoardCoordinatesModel(-1, -1);
    }

    // todo revert to private after migrating Move to coordinate system
    public BoardCoordinatesModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void checkForIllegalCoordinates() {
        checkForIllegalValue(x);
        checkForIllegalValue(y);
    }

    private void checkForIllegalValue(int coord) {
        if (coord < 0 || coord >= 3) {
            throw new IllegalCoordinatesException("Out of bounds coordinates");
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

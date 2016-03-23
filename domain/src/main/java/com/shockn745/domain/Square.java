package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;

/**
 * Represent one square of the Board
 *
 * @author Kempenich Florian
 */
public class Square {

    public final BoardCoordinatesModel coordinates;
    public final Player owner;

    public Square(BoardCoordinatesModel coordinates, Player owner) {
        this.coordinates = coordinates;
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
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

        Square square = (Square) o;

        if (coordinates != null ? !coordinates.equals(square.coordinates)
                : square.coordinates != null) {
            return false;
        }
        return owner != null ? owner.equals(square.owner) : square.owner == null;

    }
}

package com.shockn745.application.driving.dto;

/**
 * @author Kempenich Florian
 */
public class Move {
    public final BoardCoordinates coordinates;

    public final Player player;

    public Move(BoardCoordinates coordinates, Player player) {
        this.coordinates = coordinates;
        this.player = player;
    }
    
    @Override
    public String toString() {
        return "Move: x=" + coordinates.x + " y=" + coordinates.y+ " player=" + player.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Move move = (Move) o;

        if (coordinates != null ? !coordinates.equals(move.coordinates)
                : move.coordinates != null) {
            return false;
        }
        return player != null ? player.equals(move.player) : move.player == null;

    }

    @Override
    public int hashCode() {
        int result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}

package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.InvalidMoveException;

public class MoveModel {

    public final BoardCoordinatesModel coordinates;

    public final Player player;

    public MoveModel(BoardCoordinatesModel coordinates, Player player) {
        this.coordinates = coordinates;
        this.player = player;
        checkIfMoveValid();
    }

    private void checkIfMoveValid() {
        if (player == null) {
            throw new InvalidMoveException("Null player, INITIALIZE PLAYER");
        } else if (player.equals(Player.noPlayer())) {
            throw new InvalidMoveException("Invalid player. Play only with PLAYER 1 OR PLAYER 2");
        }
    }

    public boolean sameCoordinates(MoveModel other) {
        return this.coordinates.equals(other.coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoveModel moveModel = (MoveModel) o;

        if (coordinates != null ? !coordinates.equals(moveModel.coordinates)
                : moveModel.coordinates != null) {
            return false;
        }
        return player != null ? player.equals(moveModel.player) : moveModel.player == null;

    }

    @Override
    public int hashCode() {
        int result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}

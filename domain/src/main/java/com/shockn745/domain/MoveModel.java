package com.shockn745.domain;

import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.InvalidMoveException;

public class MoveModel {

    public final int x;
    public final int y;

    public final Player player;

    public MoveModel(Move move) {
        this(move.x, move.y, move.player);
    }

    public MoveModel(int x, int y, Player player) {
        this.y = y;
        this.x = x;
        this.player = player;
        checkIfMoveValid();
    }

    private void checkIfMoveValid() {
        if (isOutOfBounds()) {
            throw new InvalidMoveException("Out of bounds coordinates");
        } else if (player == null) {
            throw new InvalidMoveException("Null player, INITIALIZE PLAYER");
        } else if (player.equals(Player.noPlayer())) {
            throw new InvalidMoveException("Invalid player. Play only with PLAYER 1 OR PLAYER 2");
        }
    }

    private boolean isOutOfBounds() {
        return x < 0
                || x > 2
                || y < 0
                || y > 2;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + player.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveModel move = (MoveModel) o;

        return x == move.x && y == move.y && player.equals(move.player);

    }

    public boolean sameCoordinates(MoveModel other) {
        return x == other.x && y == other.y;
    }
}

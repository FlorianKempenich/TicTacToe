package com.shockn745.tictactoe.tictac;

public class Move {

    public static final int PLAYER_1 = 6541;
    public static final int PLAYER_2 = 3546;

    public final int x;
    public final int y;

    public final int player;

    public Move(int x, int y) throws InvalidMoveException {
        this.x = x;
        this.y = y;
        this.player = PLAYER_1;
        checkIfMoveValid();
    }

    private void checkIfMoveValid() {
        if (isOutOfBounds()) {
            throw new InvalidMoveException("Out of bounds coordinates");
        } else if (invalidPlayer()) {
            throw new InvalidMoveException("Invalid player, USE STATIC FIELDS");
        }
    }

    private boolean isOutOfBounds() {
        return x < 0
                || x > 2
                || y < 0
                || y > 2;
    }

    private boolean invalidPlayer() {
        return player != PLAYER_1 && player != PLAYER_2;
    }

    public Move(int x, int y, int player) throws InvalidMoveException {
        this.x = x;
        this.y = y;
        this.player = player;
        checkIfMoveValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return x == move.x && y == move.y && player == move.player;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + player;
        return result;
    }

    public boolean sameCoordinates(Move other) {
        return x == other.x && y == other.y;
    }
}

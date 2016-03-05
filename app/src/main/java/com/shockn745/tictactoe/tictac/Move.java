package com.shockn745.tictactoe.tictac;

public class Move {

    public final int x;
    public final int y;

    public Move(int x, int y) throws InvalidMoveException {
        this.x = x;
        this.y = y;
        checkIfMoveValid();
    }

    private void checkIfMoveValid() {
        if (isMoveInvalid()) {
            throw new InvalidMoveException();
        }
    }

    private boolean isMoveInvalid() {
        return x < 0
                || x > 2
                || y < 0
                || y > 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return x == move.x && y == move.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

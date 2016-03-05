package com.shockn745.tictactoe;

public class Move {

    public final int x;
    public final int y;

    public final int playerOld;
    public final Player player;

    public Move(int x, int y, int player) throws InvalidMoveException {
        this.x = x;
        this.y = y;
        this.player = new Player(player);
        this.playerOld = this.player.type;
        checkIfMoveValid();
    }

    public Move(int y, int x, Player player) {
        this.y = y;
        this.x = x;
        this.player = player;
        this.playerOld = this.player.type;
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
        return playerOld != Player.PLAYER_1 && playerOld != Player.PLAYER_2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return x == move.x && y == move.y && playerOld == move.playerOld;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + playerOld;
        return result;
    }

    public boolean sameCoordinates(Move other) {
        return x == other.x && y == other.y;
    }
}

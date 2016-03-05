package com.shockn745.tictactoe;

public class Player {

    public static final int NO_PLAYER = 0;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    public final int type;

    public Player(int type) throws InvalidPlayerException {
        this.type = type;
    }

    private void checkForInvalidPlayer() throws InvalidPlayerException {
        if (invalidPlayer()) {
            throw new InvalidPlayerException();
        }
    }

    public boolean invalidPlayer() {
        return type != Player.PLAYER_1 && type != Player.PLAYER_2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return type == player.type;

    }

    @Override
    public int hashCode() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case PLAYER_1:
                return "Player 1";
            case PLAYER_2:
                return "Player 2";
            case NO_PLAYER:
                return "NO_PLAYER";
            default:
                return "";
        }
    }
}

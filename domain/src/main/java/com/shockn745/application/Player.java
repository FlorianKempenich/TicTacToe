package com.shockn745.application;

import com.shockn745.domain.exceptions.InvalidPlayerException;

public class Player {

    // Unfortunately the use of enum is advised against
    private static final int NO_PLAYER = 0;
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 2;

    private final int type;

    private Player(int type) throws InvalidPlayerException {
        this.type = type;
    }

    public static Player player1() {
        return new Player(PLAYER_1);
    }

    public static Player player2() {
        return new Player(PLAYER_2);
    }

    public static Player noPlayer() {
        return new Player(NO_PLAYER);
    }

    @Override
    public int hashCode() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return type == player.type;

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

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

    private boolean invalidPlayer() {
        return type != Player.PLAYER_1 && type != Player.PLAYER_2;
    }
}

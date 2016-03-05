package com.shockn745.tictactoe.tictac;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Move> moves = new ArrayList<>(9);

    public void play(Move move) throws IllegalMoveException {
        checkForIllegalMove(move);
        moves.add(move);
    }

    private void checkForIllegalMove(Move currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException();
        }
    }

    private boolean coordinatesAlreadyPlayed(Move currentMove) {
        for (Move move: moves) {
            if (move.sameCoordinates(currentMove)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFinished() {
        int firstColum = 0;
        for (Move move: moves) {
            if (inFirstColumn(move)) {
                firstColum++;
            }
        }
        return firstColum == 3;
    }

    private boolean inFirstColumn(Move move) {
        return move.y == 0;
    }

}

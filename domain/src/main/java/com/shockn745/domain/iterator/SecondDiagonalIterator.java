package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.application.driving.Player;

public class SecondDiagonalIterator implements BoardIterator {

    private final Board board;

    private int cursor = 0;

    public SecondDiagonalIterator(Board board) {
        this.board = board;
    }

    @Override
    public boolean hasNext() {
        return cursor < 3;
    }

    @Override
    public Player next() {
        Player player = board.getPlayerAtCoordinates(cursor, 2 - cursor);
        cursor++;
        return player;
    }

    @Override
    public Player first() {
        return board.getPlayerAtCoordinates(0, 2);
    }
}

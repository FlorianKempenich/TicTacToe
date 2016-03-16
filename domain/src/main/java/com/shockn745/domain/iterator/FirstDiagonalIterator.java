package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.domain.Player;

public class FirstDiagonalIterator implements BoardIterator {

    private final Board board;

    private int cursor = 0;

    public FirstDiagonalIterator(Board board) {
        this.board = board;
    }

    @Override
    public boolean hasNext() {
        return cursor < 3;
    }

    @Override
    public Player next() {
        Player player = board.getPlayerAtCoordinates(cursor, cursor);
        cursor++;
        return player;
    }

    @Override
    public Player first() {
        return board.getPlayerAtCoordinates(0, 0);
    }
}

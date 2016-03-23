package com.shockn745.domain.iterator;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.BoardImpl;

public class SecondDiagonalIterator implements BoardIterator {

    private final BoardImpl board;

    private int cursor = 0;

    public SecondDiagonalIterator(BoardImpl board) {
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

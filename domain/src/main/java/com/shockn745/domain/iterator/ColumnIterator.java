package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.domain.Player;

public class ColumnIterator implements BoardIterator {

    private final int columnIndex;
    private final Board board;

    private int cursor = 0;

    public ColumnIterator(Board board, int columnIndex) {
        this.columnIndex = columnIndex;
        this.board = board;
    }

    @Override
    public boolean hasNext() {
        return cursor < 3;
    }

    @Override
    public Player next() {
        Player player = board.getPlayerAtCoordinates(columnIndex, cursor);
        cursor++;
        return player;
    }

    @Override
    public Player first() {
        return board.getPlayerAtCoordinates(columnIndex, 0);
    }
}

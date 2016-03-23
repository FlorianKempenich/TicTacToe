package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.domain.Square;

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
    public Square next() {
        Square square = board.getSquareAtCoordinates(columnIndex, cursor);
        cursor++;
        return square;
    }

    @Override
    public Square first() {
        return board.getSquareAtCoordinates(columnIndex, 0);
    }
}

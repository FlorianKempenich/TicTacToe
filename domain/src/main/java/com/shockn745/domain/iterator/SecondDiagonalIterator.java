package com.shockn745.domain.iterator;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Board;
import com.shockn745.domain.Square;

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
    public Square next() {
        Square square = board.getSquareAtCoordinates(cursor, 2 - cursor);
        cursor++;
        return square;
    }

    @Override
    public Square first() {
        return board.getSquareAtCoordinates(0, 2);
    }
}

package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.application.driving.Player;

public class LineIterator implements BoardIterator {

    private final int lineIndex;
    private final Board board;

    private int cursor = 0;

    public LineIterator(Board board, int lineIndex) {
        this.lineIndex = lineIndex;
        this.board = board;
    }

    @Override
    public boolean hasNext() {
        return cursor < 3;
    }

    @Override
    public Player next() {
        Player player = board.getPlayerAtCoordinates(cursor, lineIndex);
        cursor++;
        return player;
    }

    @Override
    public Player first() {
        return board.getPlayerAtCoordinates(0, lineIndex);
    }
}

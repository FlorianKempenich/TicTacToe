package com.shockn745.domain.iterator;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.BoardImpl;

public class LineIterator implements BoardIterator {

    private final int lineIndex;
    private final BoardImpl board;

    private int cursor = 0;

    public LineIterator(BoardImpl board, int lineIndex) {
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

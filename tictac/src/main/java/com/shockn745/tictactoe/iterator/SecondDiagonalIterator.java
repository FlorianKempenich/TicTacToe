package com.shockn745.tictactoe.iterator;

import com.shockn745.tictactoe.Board;
import com.shockn745.tictactoe.Player;

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
        Player player = board.playerAtCoordinates(cursor, 2 - cursor);
        cursor++;
        return player;
    }

    @Override
    public Player first() {
        return board.playerAtCoordinates(0, 2);
    }
}
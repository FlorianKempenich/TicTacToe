package com.shockn745.domain;

import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;
import com.shockn745.domain.iterator.ColumnIterator;
import com.shockn745.domain.iterator.FirstDiagonalIterator;
import com.shockn745.domain.iterator.LineIterator;
import com.shockn745.domain.iterator.SecondDiagonalIterator;

public class BoardImpl implements Board {

    private static final Player NO_PLAYER = Player.noPlayer();
    private Player[][] board = new Player[3][3];

    public BoardImpl() {
        initializeTheBoard();
    }

    private void initializeTheBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = NO_PLAYER;
            }
        }
    }

    @Override
    public Player[][] getBoardStatus() {
        return board;
    }

    @Override
    public void addMove(MoveModel move) throws IllegalMoveException {
        checkIfSquareAlreadyPlayed(move);
        addMoveToBoard(move);
    }

    private void addMoveToBoard(MoveModel currentMove) {
        int x = currentMove.x;
        int y = currentMove.y;
        board[x][y] = currentMove.player;
    }

    private void checkIfSquareAlreadyPlayed(MoveModel currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException();
        }
    }

    private boolean coordinatesAlreadyPlayed(MoveModel currentMove) {
        Player squareOwner = getPlayerAtCoordinates(currentMove.x, currentMove.y);
        return !squareOwner.equals(NO_PLAYER);
    }

    @Override
    public Player getPlayerAtCoordinates(int x, int y) {
        return board[x][y];
    }

    @Override
    public String toString() {
        return
                "\n+------ 0 ------- 1 -------- 2 ---------> X\n" +
                        "| 0  " + board[0][0] + "   " + board[1][0] + "   " + board[2][0] + "   \n" +
                        "| 1  " + board[0][1] + "   " + board[1][1] + "   " + board[2][1] + "   \n" +
                        "| 2  " + board[0][2] + "   " + board[1][2] + "   " + board[2][2] + "   \n" +
                        "v\n" +
                        "Y\n";
    }

    @Override
    public BoardIterator getLineIterator(int lineIndex) {
        return new LineIterator(this, lineIndex);
    }

    @Override
    public BoardIterator getColumnIterator(int columnIterator) {
        return new ColumnIterator(this, columnIterator);
    }

    @Override
    public BoardIterator getFirstDiagonalIterator() {
        return new FirstDiagonalIterator(this);
    }

    @Override
    public BoardIterator getSecondDiagonalIterator() {
        return new SecondDiagonalIterator(this);
    }

}

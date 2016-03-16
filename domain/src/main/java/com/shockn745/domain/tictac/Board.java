package com.shockn745.domain.tictac;

import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.tictac.iterator.BoardIterator;
import com.shockn745.domain.tictac.iterator.ColumnIterator;
import com.shockn745.domain.tictac.iterator.FirstDiagonalIterator;
import com.shockn745.domain.tictac.iterator.LineIterator;
import com.shockn745.domain.tictac.iterator.SecondDiagonalIterator;

public class Board {

    private static final Player NO_PLAYER = Player.noPlayer();
    Player[][] board = new Player[3][3];

    public Board() {
        initializeTheBoard();
    }


    private void initializeTheBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = NO_PLAYER;
            }
        }
    }

    public void addMove(Move move) throws IllegalMoveException {
        checkIfSquareAlreadyPlayed(move);
        addMoveToBoard(move);

    }

    private void addMoveToBoard(Move currentMove) {
        int x = currentMove.x;
        int y = currentMove.y;
        board[x][y] = currentMove.player;
    }

    private void checkIfSquareAlreadyPlayed(Move currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException();
        }
    }

    private boolean coordinatesAlreadyPlayed(Move currentMove) {
        Player squareOwner = playerAtCoordinates(currentMove.x, currentMove.y);
        return !squareOwner.equals(NO_PLAYER);
    }

    public Player playerAtCoordinates(int x, int y) {
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

    public BoardIterator getLineIterator(int lineIndex) {
        return new LineIterator(this, lineIndex);
    }

    public BoardIterator getColumnIterator(int columnIterator) {
        return new ColumnIterator(this, columnIterator);
    }

    public BoardIterator getFirstDiagonalIterator() {
        return new FirstDiagonalIterator(this);
    }

    public BoardIterator getSecondDiagonalIterator() {
        return new SecondDiagonalIterator(this);
    }

}

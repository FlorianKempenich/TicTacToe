package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;
import com.shockn745.domain.iterator.ColumnIterator;
import com.shockn745.domain.iterator.FirstDiagonalIterator;
import com.shockn745.domain.iterator.LineIterator;
import com.shockn745.domain.iterator.SecondDiagonalIterator;
import com.shockn745.utils.NullObjects;

import java.util.Arrays;

public class BoardImpl {

    private static final Player NO_PLAYER = Player.noPlayer();
    private Player[][] board;

    public BoardImpl(Player[][] board) {
        this.board = board;
    }

    private void initializeTheBoard() {
        board = NullObjects.makeEmptyBoard();
    }

    public Player[][] getBoard() {
        return board;
    }

    public void addMove(MoveModel move) throws IllegalMoveException {
        checkIfSquareAlreadyPlayed(move);
        addMoveToBoard(move);
    }

    private void addMoveToBoard(MoveModel currentMove) {
        int x = currentMove.coordinates.x;
        int y = currentMove.coordinates.y;
        board[x][y] = currentMove.player;
    }

    private void checkIfSquareAlreadyPlayed(MoveModel currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException("This square has already been played");
        }
    }

    private boolean coordinatesAlreadyPlayed(MoveModel currentMove) {
        int x = currentMove.coordinates.x;
        int y = currentMove.coordinates.y;
        Player squareOwner = getPlayerAtCoordinates(x, y);
        return !squareOwner.equals(NO_PLAYER);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardImpl board1 = (BoardImpl) o;

        return Arrays.deepEquals(board, board1.board);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

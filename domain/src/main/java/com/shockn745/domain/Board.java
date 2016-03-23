package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;
import com.shockn745.domain.iterator.ColumnIterator;
import com.shockn745.domain.iterator.FirstDiagonalIterator;
import com.shockn745.domain.iterator.LineIterator;
import com.shockn745.domain.iterator.SecondDiagonalIterator;

import java.util.Arrays;

public class Board {

    private static final Player NO_PLAYER = Player.noPlayer();
    private final Square[][] board;

    public Board(Square[][] board) {
        this.board = board;
    }

    public Square[][] getNewBoard() {
        return board;
    }

    public void addMove(MoveModel move) throws IllegalMoveException {
        checkIfSquareAlreadyPlayed(move);
        addMoveToBoard(move);
    }

    private void addMoveToBoard(MoveModel currentMove) {
        int x = currentMove.coordinates.x;
        int y = currentMove.coordinates.y;
        board[x][y] = new Square(BoardCoordinatesModel.fromCoordinates(x, y), currentMove.player);
    }

    private void checkIfSquareAlreadyPlayed(MoveModel currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException("This square has already been played");
        }
    }

    private boolean coordinatesAlreadyPlayed(MoveModel currentMove) {
        int x = currentMove.coordinates.x;
        int y = currentMove.coordinates.y;
        Player squareOwner = getSquareAtCoordinates(x, y).owner;
        return !squareOwner.equals(NO_PLAYER);
    }

    public Square getSquareAtCoordinates(int x, int y) {
        return board[x][y];
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
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Board board1 = (Board) o;

        return Arrays.deepEquals(board, board1.board);

    }

    @Override
    public String toString() {
        return
                "\n+------ 0 ------- 1 -------- 2 ---------> X\n" +
                        "| 0  " + board[0][0].owner + "   " + board[1][0].owner + "   "
                        + board[2][0].owner
                        + "   \n" +
                        "| 1  " + board[0][1].owner + "   " + board[1][1].owner + "   "
                        + board[2][1].owner
                        + "   \n" +
                        "| 2  " + board[0][2].owner + "   " + board[1][2].owner + "   "
                        + board[2][2].owner
                        + "   \n" +
                        "v\n" +
                        "Y\n";
    }
}

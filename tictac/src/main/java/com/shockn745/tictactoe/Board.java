package com.shockn745.tictactoe;

import com.shockn745.tictactoe.exceptions.IllegalMoveException;

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

    public Player getColumnOwner(int columnIndex) {
        return board[columnIndex][0];
    }

    public Player getFirstDiagonalOwner() {
        return getLineOwner(0);
    }

    public Player getLineOwner(int lineIndex) {
        return board[0][lineIndex];
    }

    public Player getSecondDiagonalOwner() {
        return getLineOwner(2);
    }
}

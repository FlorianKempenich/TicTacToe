package com.shockn745.tictactoe.tictac;

/**
 * Represents a game of TicTacToe.
 * <p/>
 * A game is finished when either one of the column, one of the lines, or one of the diagonal is
 * scored.
 * <p/>
 * A line is scored when the same player owns all the square in a line.
 * Same for column and diagonal.
 */
public class Game {

    private int[][] board = new int[3][3];

    public Game() {
        initializeTheBoard();
    }

    private void initializeTheBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Move.NO_PLAYER;
            }
        }
    }

    public void play(Move move) throws IllegalMoveException {
        checkForIllegalMove(move);
        addMoveToBoard(move);
    }

    private void addMoveToBoard(Move currentMove) {
        int x = currentMove.x;
        int y = currentMove.y;
        board[x][y] = currentMove.player;
    }

    private void checkForIllegalMove(Move currentMove) throws IllegalMoveException {
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException();
        }
    }

    private boolean coordinatesAlreadyPlayed(Move currentMove) {
        int squareOwner = playerAtCoordinates(currentMove.x, currentMove.y);
        return squareOwner != Move.NO_PLAYER;
    }

    public boolean isFinished() {
        for (int i = 0; i < 3; i++) {
            if (isLineScored(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineScored(int lineIndex) {
        int lineOwner = getLineOwner(lineIndex);
        return isLineOwnedByASinglePlayer(lineIndex, lineOwner);
    }

    private int getLineOwner(int lineIndex) {
        return board[0][lineIndex];
    }

    private boolean isLineOwnedByASinglePlayer(int lineIndex, int lineOwner) {
        if (lineOwner == Move.NO_PLAYER) return false;
        for (int i = 0; i < 3; i++) {
            if (playerAtCoordinates(i, lineIndex) != lineOwner) {
                return false;
            }
        }
        return true;
    }

    private int playerAtCoordinates(int x, int y) {
        return board[x][y];
    }

}

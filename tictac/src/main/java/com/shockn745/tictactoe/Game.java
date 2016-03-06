package com.shockn745.tictactoe;

import com.shockn745.tictactoe.exceptions.IllegalMoveException;

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

    private static final Player NO_PLAYER = Player.noPlayer();
    private final Board board;
    private Player previousPlayer = NO_PLAYER;

    public Game(Board board) {
        this.board = board;
    }

    public void play(Move move) throws IllegalMoveException {
        checkIfSamePlayerTwice(move);
        board.addMove(move);
    }

    private void checkIfSamePlayerTwice(Move currentMove) throws IllegalMoveException {
        if (previousPlayer.equals(currentMove.player)) {
            throw new IllegalMoveException("This player just played");
        }
        previousPlayer = currentMove.player;
    }


    public boolean isFinished() {
        for (int i = 0; i < 3; i++) {
            if (isLineScored(i) || isColumnScored(i)) {
                return true;
            }
        }
        return isFirstDiagonalFinished() || isSecondDiagonalFinished();
    }

    private boolean isColumnScored(int columnIndex) {
        Player columnOwner = board.getColumnOwner(columnIndex);
        return isColumnOwnedByASinglePlayer(columnIndex, columnOwner);
    }


    private boolean isColumnOwnedByASinglePlayer(int columnIndex, Player columnOwner) {
        if (columnOwner.equals(NO_PLAYER)) return false;
        for (int i = 0; i < 3; i++) {
            if (!board.playerAtCoordinates(columnIndex, i).equals(columnOwner)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineScored(int lineIndex) {
        Player lineOwner = board.getLineOwner(lineIndex);
        return isLineOwnedByASinglePlayer(lineIndex, lineOwner);
    }


    private boolean isLineOwnedByASinglePlayer(int lineIndex, Player lineOwner) {
        if (lineOwner.equals(NO_PLAYER)) return false;
        for (int i = 0; i < 3; i++) {
            if (!board.playerAtCoordinates(i, lineIndex).equals(lineOwner)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFirstDiagonalFinished() {
        Player diagonalOwner = board.getFirstDiagonalOwner();
        return isFirstDiagonalOwnedByASinglePlayer(diagonalOwner);
    }

    private boolean isFirstDiagonalOwnedByASinglePlayer(Player diagonalOwner) {
        for (int i = 0; i < 3; i++) {
            if (!board.playerAtCoordinates(i, i).equals(diagonalOwner)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSecondDiagonalFinished() {
        Player diagonalOwner = board.getSecondDiagonalOwner();
        return isSecondDiagonalOwnedByASinglePlayer(diagonalOwner);
    }

    private boolean isSecondDiagonalOwnedByASinglePlayer(Player diagonalOwner) {
        for (int i = 0; i < 3; i++) {
            if (!board.playerAtCoordinates(i, 2 - i).equals(diagonalOwner)) {
                return false;
            }
        }
        return true;
    }

}

package com.shockn745.tictactoe;

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

    private Player[][] board = new Player[3][3];
    private Player previousPlayer = NO_PLAYER;

    public Game() {
        initializeTheBoard();
    }

    private void initializeTheBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = NO_PLAYER;
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
        if (previousPlayer.equals(currentMove.player)) {
            throw new IllegalMoveException("This player just played");
        }
        previousPlayer = currentMove.player;
        if (coordinatesAlreadyPlayed(currentMove)) {
            throw new IllegalMoveException();
        }
    }

    private boolean coordinatesAlreadyPlayed(Move currentMove) {
        Player squareOwner = playerAtCoordinates(currentMove.x, currentMove.y);
        return !squareOwner.equals(NO_PLAYER);
    }

    private Player playerAtCoordinates(int x, int y) {
        return board[x][y];
    }

    public boolean isFinished() {
        for (int i = 0; i < 3; i++) {
            if (isLineScored(i) || isColumnScored(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isColumnScored(int columnIndex) {
        Player columnOwner = getColumnOwner(columnIndex);
        return isColumnOwnedByASinglePlayer(columnIndex, columnOwner);
    }

    private Player getColumnOwner(int columnIndex) {
        return board[columnIndex][0];
    }

    private boolean isColumnOwnedByASinglePlayer(int columnIndex, Player columnOwner) {
        if (columnOwner.equals(NO_PLAYER)) return false;
        for (int i = 0; i < 3; i++) {
            if (!playerAtCoordinates(columnIndex, i).equals(columnOwner)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineScored(int lineIndex) {
        Player lineOwner = getLineOwner(lineIndex);
        return isLineOwnedByASinglePlayer(lineIndex, lineOwner);
    }

    private Player getLineOwner(int lineIndex) {
        return board[0][lineIndex];
    }

    private boolean isLineOwnedByASinglePlayer(int lineIndex, Player lineOwner) {
        if (lineOwner.equals(NO_PLAYER)) return false;
        for (int i = 0; i < 3; i++) {
            if (!playerAtCoordinates(i, lineIndex).equals(lineOwner)) {
                return false;
            }
        }
        return true;
    }
}

package com.shockn745.tictactoe;

import com.shockn745.tictactoe.exceptions.IllegalMoveException;
import com.shockn745.tictactoe.iterator.BoardIterator;
import com.shockn745.tictactoe.iterator.ColumnIterator;
import com.shockn745.tictactoe.iterator.FirstDiagonalIterator;
import com.shockn745.tictactoe.iterator.LineIterator;
import com.shockn745.tictactoe.iterator.SecondDiagonalIterator;

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

    private Player winner = Player.noPlayer();

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

    public boolean isFinished() { //todo change logic or naming : "isFinished" should not update the winner conceptually speaking
        for (int i = 0; i < 3; i++) {
            BoardIterator lineIterator = new LineIterator(board, i); // todo put iterator in the board
            BoardIterator columnIterator = new ColumnIterator(board, i);
            if (isSequenceScored(lineIterator) || isSequenceScored(columnIterator)) {
                return true;
            }
        }
        BoardIterator firstDiagonalIterator = new FirstDiagonalIterator(board);
        BoardIterator secondDiagonalIterator = new SecondDiagonalIterator(board);
        return isSequenceScored(firstDiagonalIterator) || isSequenceScored(secondDiagonalIterator);
    }

    private boolean isSequenceScored(BoardIterator iterator) {
        Player sequenceWinner = getSequenceWinner(iterator);
        if (!sequenceWinner.equals(Player.noPlayer())) {
            winner = sequenceWinner;
            return true;
        } else {
            return false;
        }
    }

    private Player getSequenceWinner(BoardIterator iterator) {
        Player sequenceWinner = Player.noPlayer();
        Player sequenceOwner = iterator.first();
        boolean scored = isSequenceOwnedBySamePlayer(iterator, sequenceOwner);
        if (scored) {
            sequenceWinner = sequenceOwner;
        }
        return sequenceWinner;
    }

    private boolean isSequenceOwnedBySamePlayer(BoardIterator iterator, Player sequenceOwner) {
        if (sequenceOwner.equals(NO_PLAYER)) return false;
        while (iterator.hasNext()) {
            if (!iterator.next().equals(sequenceOwner)) return false;
        }
        return true;
    }

    public Player getWinner() {
        return winner;
    }

}

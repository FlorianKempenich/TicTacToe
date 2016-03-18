package com.shockn745.domain;

import com.shockn745.application.GameStatus;
import com.shockn745.domain.exceptions.GameNotFinishedException;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;

/**
 * Represents a game of TicTacToe. <p/> A game is finished when either one of the column, one of the
 * lines, or one of the diagonal is scored. <p/> A line is scored when the same player owns all the
 * square in a line. Same for column and diagonal.
 */
public class GameImpl implements Game {

    private static final Player NO_PLAYER = Player.noPlayer();
    private final Board board;
    private Player previousPlayer = NO_PLAYER;

    private Player winner = NO_PLAYER;

    public GameImpl(Board board) {
        this.board = board;
    }

    @Override
    public void play(MoveModel move) throws IllegalMoveException {
        checkIfSamePlayerTwice(move);
        board.addMove(move);
    }

    private void checkIfSamePlayerTwice(MoveModel currentMove) throws IllegalMoveException {
        if (previousPlayer.equals(currentMove.player)) {
            throw new IllegalMoveException("This player just played");
        }
        previousPlayer = currentMove.player;
    }

    /**
     * @return True if the game is finished, False otherwise
     */
    @Override
    public boolean checkIfFinishedAndUpdateWinner() {
        /*
        Not sure that it's a good idea to mix responsibilities like this but the only other
        solution I could think of would be to do the iteration process all over again when looking
        for the winner. Which is also a terrible idea.
        So no idea how to make this better . . . any idea ? Drop me a mail, or a pull request
         */
        for (int i = 0; i < 3; i++) {
            BoardIterator lineIterator = board.getLineIterator(i);
            BoardIterator columnIterator = board.getColumnIterator(i);
            if (isSequenceScored(lineIterator) || isSequenceScored(columnIterator)) {
                return true;
            }
        }
        BoardIterator firstDiagonalIterator = board.getFirstDiagonalIterator();
        BoardIterator secondDiagonalIterator = board.getSecondDiagonalIterator();
        return isSequenceScored(firstDiagonalIterator) || isSequenceScored(secondDiagonalIterator);
    }

    private boolean isSequenceScored(BoardIterator iterator) {
        Player sequenceWinner = getSequenceWinner(iterator);
        if (!sequenceWinner.equals(NO_PLAYER)) {
            winner = sequenceWinner;
            return true;
        } else {
            return false;
        }
    }

    private Player getSequenceWinner(BoardIterator iterator) {
        Player sequenceWinner = NO_PLAYER;
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

    @Override
    public Player getWinner() throws GameNotFinishedException {
        if (winner.equals(NO_PLAYER)) {
            throw new GameNotFinishedException();
        }
        return winner;
    }

    @Override
    public GameStatus makeStatus(int id) {
        return new GameStatus(
                id,
                board.getBoardStatus(),
                previousPlayer,
                winner
        );
    }
}

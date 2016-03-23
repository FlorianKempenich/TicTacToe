package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a game of TicTacToe. <p/> A game is finished when either one of the column, one of the
 * lines, or one of the diagonal is scored. <p/> A line is scored when the same player owns all the
 * square in a line. Same for column and diagonal.
 */
public class Game {

    private static final Player NO_PLAYER = Player.noPlayer();
    private final Board board;
    private final int gameId;
    private Player previousPlayer = NO_PLAYER;
    private BoardCoordinatesModel lastSquarePlayed;
    private Set<BoardCoordinatesModel> winningSquare;

    private Player winner = NO_PLAYER;

    public Game(Board board, int gameId, Player previousPlayer, Player winner, BoardCoordinatesModel lastSquarePlayed) {
        this.board = board;
        this.gameId = gameId;
        this.previousPlayer = previousPlayer;
        this.winner = winner;
        this.lastSquarePlayed = lastSquarePlayed;
        this.winningSquare = new HashSet<>(3); //todo inject
    }

    public void play(MoveModel move) throws IllegalMoveException {
        checkIfSamePlayerTwice(move);
        board.addMove(move);
        previousPlayer = move.player;
        lastSquarePlayed = move.coordinates;
    }

    private void checkIfSamePlayerTwice(MoveModel currentMove) throws IllegalMoveException {
        if (previousPlayer.equals(currentMove.player)) {
            throw new IllegalMoveException("This player just played");
        }
    }

    /**
     * @return True if the game is finished, False otherwise
     */
    public boolean checkIfFinishedAndUpdateWinStatus() {
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

            // Update winning squares

            return true;
        } else {
            return false;
        }
    }

    private Player getSequenceWinner(BoardIterator iterator) {
        Player sequenceWinner = NO_PLAYER;
        Player sequenceOwner = iterator.first().owner;
        boolean scored = isSequenceOwnedBySamePlayer(iterator, sequenceOwner);
        if (scored) {
            sequenceWinner = sequenceOwner;
        }
        return sequenceWinner;
    }

    private boolean isSequenceOwnedBySamePlayer(BoardIterator iterator, Player sequenceOwner) {
        if (sequenceOwner.equals(NO_PLAYER)) {
            return false;
        }
        while (iterator.hasNext()) {
            Player nextPlayer = iterator.next().owner;
            if (!nextPlayer.equals(sequenceOwner)) {
                return false;
            }
        }
        return true;
    }

    public Board getBoard() {
        return board;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public BoardCoordinatesModel getLastSquarePlayed() {
        return lastSquarePlayed;
    }

    public Player getWinner() {
        return winner;
    }

    public Set<BoardCoordinatesModel> getWinningSquares() {
        Set<BoardCoordinatesModel> expectedFirstRow = new HashSet<>(3);
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(0,0));
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(1,0));
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(2,0));
        return expectedFirstRow;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (previousPlayer != null ? previousPlayer.hashCode() : 0);
        result = 31 * result + gameId;
        result = 31 * result + (lastSquarePlayed != null ? lastSquarePlayed.hashCode() : 0);
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Game game = (Game) o;

        if (gameId != game.gameId) {
            return false;
        }
        if (board != null ? !board.equals(game.board) : game.board != null) {
            return false;
        }
        if (previousPlayer != null ? !previousPlayer.equals(game.previousPlayer)
                : game.previousPlayer != null) {
            return false;
        }
        if (lastSquarePlayed != null ? !lastSquarePlayed.equals(game.lastSquarePlayed)
                : game.lastSquarePlayed != null) {
            return false;
        }
        return winner != null ? winner.equals(game.winner) : game.winner == null;

    }
}

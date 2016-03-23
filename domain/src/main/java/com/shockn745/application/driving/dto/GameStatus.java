package com.shockn745.application.driving.dto;

import com.shockn745.domain.Board;
import com.shockn745.domain.Square;

import java.util.Arrays;

/**
 * @author Kempenich Florian
 */
public class GameStatus {

    public static final int NO_ID = -1;

    public final int gameId;
    public final Square[][] board;
    public final Player lastPlayer;
    public final Player winner;
    public final BoardCoordinates lastPlayedSquare;

    public GameStatus(int gameId, Square[][] board, Player lastPlayer, Player winner, BoardCoordinates lastPlayedSquare) {
        this.gameId = gameId;
        this.board = board;
        this.lastPlayer = lastPlayer;
        this.winner = winner;
        this.lastPlayedSquare = lastPlayedSquare;
    }

    /**
     * Return a new GameStatus with an updated id
     *
     * @param id Id to assign to this GameStatus
     * @return New GameStatus with updated id
     */
    public GameStatus updateWithId(int id) {
        return new GameStatus(id, this.board, this.lastPlayer, this.winner, this.lastPlayedSquare);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameStatus status = (GameStatus) o;

        if (gameId != status.gameId) {
            return false;
        }
        if (!Arrays.deepEquals(board, status.board)) {
            return false;
        }
        if (lastPlayer != null ? !lastPlayer.equals(status.lastPlayer)
                : status.lastPlayer != null) {
            return false;
        }
        if (winner != null ? !winner.equals(status.winner) : status.winner != null) {
            return false;
        }
        return lastPlayedSquare != null ? lastPlayedSquare.equals(status.lastPlayedSquare)
                : status.lastPlayedSquare == null;

    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + Arrays.deepHashCode(board);
        result = 31 * result + (lastPlayer != null ? lastPlayer.hashCode() : 0);
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        result = 31 * result + (lastPlayedSquare != null ? lastPlayedSquare.hashCode() : 0);
        return result;
    }
}

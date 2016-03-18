package com.shockn745.application;

import com.shockn745.domain.Player;
import java.util.Arrays;

/**
 * @author Kempenich Florian
 */
public class GameStatus {

  public final int gameId;
  public final Player[][] board;
  public final Player previousPlayer;
  public final Player winner;

  public GameStatus(int gameId, Player[][] board, Player previousPlayer, Player winner) {
    this.gameId = gameId;
    this.board = board;
    this.previousPlayer = previousPlayer;
    this.winner = winner;
  }

  @Override public int hashCode() {
    int result = gameId;
    result = 31 * result + Arrays.deepHashCode(board);
    result = 31 * result + (previousPlayer != null ? previousPlayer.hashCode() : 0);
    result = 31 * result + (winner != null ? winner.hashCode() : 0);
    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GameStatus that = (GameStatus) o;

    if (gameId != that.gameId) return false;
    if (!Arrays.deepEquals(board, that.board)) return false;
    return previousPlayer != null ? previousPlayer.equals(that.previousPlayer)
        : that.previousPlayer == null && (winner != null ? winner.equals(that.winner)
            : that.winner == null);
  }
}

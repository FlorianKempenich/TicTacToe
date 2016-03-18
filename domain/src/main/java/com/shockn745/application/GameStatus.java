package com.shockn745.application;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GameStatus status = (GameStatus) o;

    if (gameId != status.gameId) return false;
    if (!Arrays.deepEquals(board, status.board)) return false;
    if (previousPlayer != null ? !previousPlayer.equals(status.previousPlayer) : status.previousPlayer != null)
      return false;
    return winner != null ? winner.equals(status.winner) : status.winner == null;

  }

  @Override
  public int hashCode() {
    int result = gameId;
    result = 31 * result + Arrays.deepHashCode(board);
    result = 31 * result + (previousPlayer != null ? previousPlayer.hashCode() : 0);
    result = 31 * result + (winner != null ? winner.hashCode() : 0);
    return result;
  }
}

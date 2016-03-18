package com.shockn745.application;

import com.shockn745.domain.Player;

/**
 * @author Kempenich Florian
 */
public class UseCaseTestUtil {

    public static GameStatus makeEmptyGameStatus(int id) {
        Player[][] board = makeEmtpyBoard();
        return new GameStatus(id, board, Player.noPlayer(), Player.noPlayer());
    }

    public static Player[][] makeEmtpyBoard() {
        Player[][] board = new Player[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.noPlayer();
            }
        }

        return board;
    }
}

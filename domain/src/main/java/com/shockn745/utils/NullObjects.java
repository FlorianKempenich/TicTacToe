package com.shockn745.utils;

import com.shockn745.application.GameStatus;
import com.shockn745.application.Player;

/**
 * @author Kempenich Florian
 */
public class NullObjects {

    public static GameStatus makeEmptyGameStatus(int id) {
        Player[][] board = makeEmptyBoard();
        return new GameStatus(id, board, Player.noPlayer(), Player.noPlayer());
    }

    public static Player[][] makeEmptyBoard() {
        Player[][] board = new Player[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.noPlayer();
            }
        }

        return board;
    }
}

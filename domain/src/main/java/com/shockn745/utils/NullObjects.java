package com.shockn745.utils;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Square;

/**
 * @author Kempenich Florian
 */
public class NullObjects {

    public static GameStatus makeEmptyGameStatus(int id) {
        Square[][] board = makeEmptyBoard();
        return new GameStatus(
                id,
                board,
                Player.noPlayer(),
                Player.noPlayer(),
                BoardCoordinates.noCoordinates()
        );
    }

    public static Player[][] makeEmptyOldBoard() {
        Player[][] board = new Player[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.noPlayer();
            }
        }

        return board;
    }

    public static Square[][] makeEmptyBoard() {
        Square[][] board = new Square[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] =
                        new Square(BoardCoordinatesModel.fromCoordinates(i, j), Player.noPlayer());
            }
        }

        return board;
    }
}

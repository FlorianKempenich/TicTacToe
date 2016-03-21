package com.shockn745.presentation.testutils;

import com.shockn745.application.driving.GameStatus;
import com.shockn745.application.driving.Player;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameStatusUtil {

    public static GameStatus makeAfterFirstMoveOn00(int gameId) {
        Player[][] boardWithMoveOn00Square = NullObjects.makeEmptyBoard();
        boardWithMoveOn00Square[0][0] = Player.player1();
        return new GameStatus(gameId, boardWithMoveOn00Square, Player.player1(), Player.noPlayer());
    }
}

package com.shockn745.testutil;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.GameFactory;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameStatusTestScenarios {

    private final GameFactory factory;

    public GameStatusTestScenarios(GameFactory factory) {
        this.factory = factory;
    }

    public GameStatus makeEmptyGameStatus(int gameId) {
        Player[][] emptyBoard = NullObjects.makeEmptyBoard();
        Player lastPlayer = Player.noPlayer();
        Player winner = Player.noPlayer();

        return new GameStatus(gameId, emptyBoard, lastPlayer, winner, BoardCoordinates.noCoordinates());
    }

    public GameStatus makeGameStatusWithMoveOn00(int gameId) throws Exception {
        Player[][] moveOn00 = NullObjects.makeEmptyBoard();
        moveOn00[0][0] = Player.player1();
        Player lastPlayer = Player.player1();
        Player winner = Player.noPlayer();

        return new GameStatus(gameId, moveOn00, lastPlayer, winner, new BoardCoordinates(0, 0));
    }

    public GameStatus makeGameStatusPlayer1WonFirstRow(int gameId) {
        Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = Player.player1();
        expectedBoard[1][1] = Player.player2();
        expectedBoard[1][0] = Player.player1();
        expectedBoard[2][2] = Player.player2();
        expectedBoard[2][0] = Player.player1();

        Player lastPlayer = Player.player1();
        Player winner = Player.player1();

        return new GameStatus(gameId, expectedBoard, lastPlayer, winner, new BoardCoordinates(2, 0));
    }
}

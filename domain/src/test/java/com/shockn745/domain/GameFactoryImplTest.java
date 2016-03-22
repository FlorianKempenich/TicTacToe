package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class GameFactoryImplTest {

    GameFactory gameFactory;

    @Before
    public void setUp() throws Exception {
        gameFactory = new GameFactoryImpl();
    }

    @Test
    public void createNewGame_returnEmptyGame() throws Exception {
        Game game = gameFactory.makeNewGame();
        GameStatus status = game.makeStatus();
        GameStatus expected = NullObjects.makeEmptyGameStatus(GameStatus.NO_ID);
        assertEquals(expected, status);
    }

    @Test
    public void createGameFromStatus_emptyBoard_resultingStatusIsSameAsOriginal() throws Exception {
        int id = 2;
        GameStatus status = NullObjects.makeEmptyGameStatus(id);
        Game game = gameFactory.makeGame(status);

        assertEquals(status, game.makeStatus());
    }

    @Test
    public void createGameFromStatus_nonEmptyBoard_resultingStatusIsSameAsOriginal() throws Exception {
        int id = 2;
        Player[][] moveOn00Board = NullObjects.makeEmptyBoard();
        moveOn00Board[0][0] = Player.player1();
        GameStatus status = new GameStatus(id, moveOn00Board, Player.player1(), Player.noPlayer());

        Game game = gameFactory.makeGame(status);
        assertEquals(status, game.makeStatus());
    }
}
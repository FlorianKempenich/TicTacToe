package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.factory.GameFactory;
import com.shockn745.domain.factory.GameFactoryImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        GameFactory factory = new GameFactoryImpl();
        game = factory.newGame();
    }

    @Test(expected = IllegalMoveException.class)
    public void playTwoMovesSameSpot_ThrowIllegalMoveException() throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player2()));
    }

    @Test
    public void samePlayerPlays2Rounds_throwException() throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        try {
            game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 1), Player.player1()));
            fail();
        } catch (IllegalMoveException e) {
            assertEquals("This player just played", e.getMessage());
        }
    }

    @Test
    public void threeInALine_samePlayer_gameIsFinished() throws Exception {
        scoreRowPlayerOne(game, 0);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    private static void scoreRowPlayerOne(Game game, int lineIndex) throws Exception {
        int otherLine = lineIndex == 0 ? 1 : 0;
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(0, lineIndex),
                Player.player1()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(2, otherLine),
                Player.player2()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(1, lineIndex),
                Player.player1()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(1, otherLine),
                Player.player2()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(2, lineIndex),
                Player.player1()
        ));
    }

    @Test
    public void threeInALine_differentLine_samePlayer_gameIsFinished() throws Exception {
        scoreRowPlayerOne(game, 1);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    @Test
    public void threeInALine_differentPlayers_gameIsNOTFinished() throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 0), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 0), Player.player1()));

        assertFalse("Game should NOT be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    @Test
    public void threeInAColumn_samePlayer_gameIsFinished() throws Exception {
        scoreColumnPlayerOne(game, 0);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    private static void scoreColumnPlayerOne(Game game, int columnIndex) throws Exception {
        int otherColumn = columnIndex == 0 ? 1 : 0;
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(columnIndex, 0),
                Player.player1()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(otherColumn, 2),
                Player.player2()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(columnIndex, 1),
                Player.player1()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(otherColumn, 0),
                Player.player2()
        ));
        game.play(new MoveModel(
                BoardCoordinatesModel.fromCoordinates(columnIndex, 2),
                Player.player1()
        ));
    }

    @Test
    public void threeInFirstDiagonal_gameIsFinished() throws Exception {
        scoreFirstDiagonalPlayerOne(game);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    private static void scoreFirstDiagonalPlayerOne(Game game) throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 0), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 1), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 0), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 2), Player.player1()));
    }

    @Test
    public void threeInSecondDiagonal_gameIsFinished() throws Exception {
        scoreSecondDiagonalPlayerOne(game);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinStatus());
    }

    private static void scoreSecondDiagonalPlayerOne(Game game) throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 2), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 1), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 1), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 0), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 0), Player.player1()));
    }

    @Test
    public void gameFinished_column_getWinner() throws Exception {
        scoreColumnPlayerOne(game, 0);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());
        assertEquals(Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_line_getWinner() throws Exception {
        scoreRowPlayerOne(game, 1);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());
        assertEquals(Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_firstDiagonal_getWinner() throws Exception {
        scoreFirstDiagonalPlayerOne(game);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());
        assertEquals(Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_secondDiagonal_getWinner() throws Exception {
        scoreSecondDiagonalPlayerOne(game);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());
        assertEquals(Player.player1(), game.getWinner());
    }

    @Test
    public void gameIsNotFinished_winnerIsNoPlayer() throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 2), Player.player1()));
        assertEquals(Player.noPlayer(), game.getWinner());
    }

    @Test
    public void illegalMoveOnOccupiedSquare_doNotSavePreviousPlayer() throws Exception {
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));

        try {
            game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player2()));
            fail();
        } catch (IllegalMoveException e) {
            assertEquals(Player.player1(), game.getPreviousPlayer());
        }
    }

    @Test
    public void gameFinished_firstRow_getLastPlayedSquare() throws Exception {
        scoreRowPlayerOne(game, 0);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());
        BoardCoordinatesModel expected = BoardCoordinatesModel.fromCoordinates(2, 0); //last square played by player 1

        assertEquals(expected, game.getLastSquarePlayed());
    }

    @Test
    public void gameFinished_firstRow_getWinningSquares() throws Exception {
        scoreRowPlayerOne(game, 0);
        assertTrue(game.checkIfFinishedAndUpdateWinStatus());

        Set<BoardCoordinatesModel> expectedFirstRow = new HashSet<>(3);
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(0,0));
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(1,0));
        expectedFirstRow.add(BoardCoordinatesModel.fromCoordinates(2,0));

        assertEquals(expectedFirstRow, game.getWinningSquares());
    }

}

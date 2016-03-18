package com.shockn745.domain;

import com.shockn745.domain.exceptions.GameNotFinishedException;
import com.shockn745.domain.exceptions.IllegalMoveException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GameImplTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        Board board = new BoardImpl();
        game = new GameImpl(board);

    }

    @Test(expected = IllegalMoveException.class)
    public void playTwoMovesSameSpot_ThrowIllegalMoveException() throws Exception {
        game.play(new MoveModel(0, 0, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(0, 0, com.shockn745.application.Player.player2()));
    }

    @Test
    public void samePlayerPlays2Rounds_throwException() throws Exception {
        game.play(new MoveModel(0, 0, com.shockn745.application.Player.player1()));
        try {
            game.play(new MoveModel(0, 1, com.shockn745.application.Player.player1()));
            fail();
        } catch (IllegalMoveException e) {
            assertEquals("This player just played", e.getMessage());
        }

    }

    @Test
    public void threeInALine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(game, 0);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinner());
    }

    private static void scoreLinePlayerOne(Game game, int lineIndex) throws Exception {
        int otherLine = lineIndex == 0 ? 1 : 0;
        game.play(new MoveModel(0, lineIndex, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(2, otherLine, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(1, lineIndex, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(1, otherLine, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(2, lineIndex, com.shockn745.application.Player.player1()));
    }

    @Test
    public void threeInALine_differentLine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(game, 1);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinner());
    }

    @Test
    public void threeInALine_differentPlayers_gameIsNOTFinished() throws Exception {
        game.play(new MoveModel(0, 0, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(1, 0, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(2, 0, com.shockn745.application.Player.player1()));

        assertFalse("Game should NOT be finished", game.checkIfFinishedAndUpdateWinner());
    }

    @Test
    public void threeInAColumn_samePlayer_gameIsFinished() throws Exception {
        scoreColumnPlayerOne(game, 0);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinner());
    }

    private static void scoreColumnPlayerOne(Game game, int columnIndex) throws Exception {
        int otherColumn = columnIndex == 0 ? 1 : 0;
        game.play(new MoveModel(columnIndex, 0, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(otherColumn, 2, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(columnIndex, 1, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(otherColumn, 0, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(columnIndex, 2, com.shockn745.application.Player.player1()));
    }

    @Test
    public void threeInFirstDiagonal_gameIsFinished() throws Exception {
        scoreFirstDiagonalPlayerOne(game);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinner());
    }

    private static void scoreFirstDiagonalPlayerOne(Game game) throws Exception {
        game.play(new MoveModel(0, 0, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(2, 0, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(1, 1, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(1, 0, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(2, 2, com.shockn745.application.Player.player1()));
    }

    @Test
    public void threeInSecondDiagonal_gameIsFinished() throws Exception {
        scoreSecondDiagonalPlayerOne(game);
        assertTrue("Game should be finished", game.checkIfFinishedAndUpdateWinner());
    }

    private static void scoreSecondDiagonalPlayerOne(Game game) throws Exception {
        game.play(new MoveModel(0, 2, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(2, 1, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(1, 1, com.shockn745.application.Player.player1()));
        game.play(new MoveModel(1, 0, com.shockn745.application.Player.player2()));
        game.play(new MoveModel(2, 0, com.shockn745.application.Player.player1()));
    }

    @Test
    public void gameFinished_column_getWinner() throws Exception {
        scoreColumnPlayerOne(game, 0);
        assertTrue(game.checkIfFinishedAndUpdateWinner());
        assertEquals(com.shockn745.application.Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_line_getWinner() throws Exception {
        scoreLinePlayerOne(game, 1);
        assertTrue(game.checkIfFinishedAndUpdateWinner());
        assertEquals(com.shockn745.application.Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_firstDiagonal_getWinner() throws Exception {
        scoreFirstDiagonalPlayerOne(game);
        assertTrue(game.checkIfFinishedAndUpdateWinner());
        assertEquals(com.shockn745.application.Player.player1(), game.getWinner());
    }

    @Test
    public void gameFinished_secondDiagonal_getWinner() throws Exception {
        scoreSecondDiagonalPlayerOne(game);
        assertTrue(game.checkIfFinishedAndUpdateWinner());
        assertEquals(com.shockn745.application.Player.player1(), game.getWinner());
    }

    @Test(expected = GameNotFinishedException.class)
    public void gameIsNotFinished_tryToGetTheWinner_throwException() throws Exception {
        game.play(new MoveModel(1, 2, com.shockn745.application.Player.player1()));
        game.getWinner();
    }
}

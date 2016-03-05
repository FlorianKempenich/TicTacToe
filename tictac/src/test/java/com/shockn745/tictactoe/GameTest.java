package com.shockn745.tictactoe;

import com.shockn745.tictactoe.exceptions.IllegalMoveException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GameTest {

    private Game game;

    private static void scoreLinePlayerOne(Game game, int lineIndex) throws Exception {
        int otherLine = lineIndex == 0 ? 1 : 0;
        game.play(new Move(0, lineIndex, Player.player1()));
        game.play(new Move(2, otherLine, Player.player2()));
        game.play(new Move(1, lineIndex, Player.player1()));
        game.play(new Move(1, otherLine, Player.player2()));
        game.play(new Move(2, lineIndex, Player.player1()));
    }

    private static void scoreColumnPlayerOne(Game game, int columnIndex) throws Exception {
        int otherColumn = columnIndex == 0 ? 1 : 0;
        game.play(new Move(columnIndex, 0, Player.player1()));
        game.play(new Move(otherColumn, 2, Player.player2()));
        game.play(new Move(columnIndex, 1, Player.player1()));
        game.play(new Move(otherColumn, 0, Player.player2()));
        game.play(new Move(columnIndex, 2, Player.player1()));
    }

    @Before
    public void setUp() throws Exception {
        Board board = new Board();
        game = new Game(board);

    }

    @Test(expected = IllegalMoveException.class)
    public void playTwoMovesSameSpot_ThrowIllegalMoveException() throws Exception {
        game.play(new Move(0, 0, Player.player1()));
        game.play(new Move(0, 0, Player.player2()));
    }

    @Test
    public void samePlayerPlays2Rounds_throwException() throws Exception {
        game.play(new Move(0, 0, Player.player1()));
        try {
            game.play(new Move(0, 1, Player.player1()));
            fail();
        } catch (IllegalMoveException e) {
            assertEquals("This player just played", e.getMessage());
        }

    }

    @Test
    public void threeInALine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(game, 0);
        assertTrue("Game should be finished", game.isFinished());
    }

    @Test
    public void threeInALine_differentLine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(game, 1);
        assertTrue("Game should be finished", game.isFinished());
    }

    @Test
    public void threeInALine_differentPlayers_gameIsNOTFinished() throws Exception {
        game.play(new Move(0, 0, Player.player1()));
        game.play(new Move(1, 0, Player.player2()));
        game.play(new Move(2, 0, Player.player1()));

        assertFalse("Game should NOT be finished", game.isFinished());
    }

    @Test
    public void threeInAColumn_samePlayer_gameIsFinished() throws Exception {
        scoreColumnPlayerOne(game, 0);
        assertTrue("Game should be finished", game.isFinished());
    }
}

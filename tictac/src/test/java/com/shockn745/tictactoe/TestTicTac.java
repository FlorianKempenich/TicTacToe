package com.shockn745.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestTicTac {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();

    }

    @Test(expected = IllegalMoveException.class)
    public void playTwoMovesSameSpot_ThrowIllegalMoveException() throws Exception {
        game.play(new Move(0, 0, Player.PLAYER_1));
        game.play(new Move(0, 0, Player.PLAYER_2));
    }

    @Test
    public void samePlayerPlays2Rounds_throwException() throws Exception {
        game.play(new Move(0, 0, Player.PLAYER_1));
        try {
            game.play(new Move(0, 1, Player.PLAYER_1));
            fail();
        } catch (IllegalMoveException e) {
            assertEquals("This player just played", e.getMessage());
        }

    }

    @Test
    public void threeInALine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(0);
        assertTrue("Game should be finished", game.isFinished());
    }

    private void scoreLinePlayerOne(int lineIndex) throws Exception {
        int otherLine = lineIndex == 0 ? 1 : 0;
        game.play(new Move(0, lineIndex, Player.PLAYER_1));
        game.play(new Move(2, otherLine, Player.PLAYER_2));
        game.play(new Move(1, lineIndex, Player.PLAYER_1));
        game.play(new Move(1, otherLine, Player.PLAYER_2));
        game.play(new Move(2, lineIndex, Player.PLAYER_1));
    }

    @Test
    public void threeInALine_differentLine_samePlayer_gameIsFinished() throws Exception {
        scoreLinePlayerOne(1);
        assertTrue("Game should be finished", game.isFinished());
    }

    @Test
    public void threeInAColumn_differentPlayers_gameIsNOTFinished() throws Exception {
        game.play(new Move(0, 0, Player.PLAYER_1));
        game.play(new Move(1, 0, Player.PLAYER_2));
        game.play(new Move(2, 0, Player.PLAYER_1));

        assertFalse("Game should NOT be finished", game.isFinished());
    }
}

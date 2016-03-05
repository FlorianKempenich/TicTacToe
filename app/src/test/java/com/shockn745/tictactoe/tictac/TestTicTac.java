package com.shockn745.tictactoe.tictac;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTicTac {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();

    }

    @Test (expected = IllegalMoveException.class)
    public void playTwoMovesSameSpot_ThrowIllegalMoveExceptionnew() throws Exception {
        game.play(new Move(0, 0, Move.PLAYER_1));
        game.play(new Move(0, 0, Move.PLAYER_2));
    }

    @Test
    public void threeInALine_samePlayer_gameIsFinished() throws Exception {
        game.play(new Move(0, 0, Move.PLAYER_1));
        game.play(new Move(1, 0, Move.PLAYER_1));
        game.play(new Move(2, 0, Move.PLAYER_1));

        assertTrue("Game should be finished", game.isFinished());
    }

    @Test
    public void threeInALine_differentLine_samePlayer_gameIsFinished() throws Exception {
        game.play(new Move(0, 1, Move.PLAYER_1));
        game.play(new Move(1, 1, Move.PLAYER_1));
        game.play(new Move(2, 1, Move.PLAYER_1));

        assertTrue("Game should be finished", game.isFinished());
    }

    @Test
    public void threeInAColumn_differentPlayers_gameIsFinished() throws Exception {
        game.play(new Move(0, 0, Move.PLAYER_1));
        game.play(new Move(1, 0, Move.PLAYER_2));
        game.play(new Move(2, 0, Move.PLAYER_1));

        assertFalse("Game should NOT be finished", game.isFinished());
    }

}

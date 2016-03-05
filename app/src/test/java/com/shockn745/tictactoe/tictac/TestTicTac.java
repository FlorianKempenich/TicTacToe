package com.shockn745.tictactoe.tictac;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
    @Ignore
    public void threeInAColumm_gameIsFinished() throws Exception {
//        playPlayerOne(new Move(0, 0));
//        playPlayerOne(new Move(0, 1));
//        playPlayerOne(new Move(0, 2));
//
//        assertTrue("Game should be finished", game.isFinished());
    }

}

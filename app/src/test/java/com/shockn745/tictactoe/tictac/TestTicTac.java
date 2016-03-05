package com.shockn745.tictactoe.tictac;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTicTac {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();

    }

    @Test
    public void testHappyPath_Move() throws Exception {
        Move move = new Move(0,2);

        assertEquals(0, move.x);
        assertEquals(2, move.y);
    }

    @Test (expected = InvalidMoveException.class)
    public void invalidMove_throwException() throws Exception {
        new Move(22,34);
    }

    @Test
    public void testEqualityOnMoves() throws Exception {
        assertEquals(new Move(0,0), new Move(0,0));

    }

    @Test (expected = IllegalMoveException.class)
    @Ignore
    public void playTwoMovesSameSpot_ThrowIllegalMoveException() throws Exception {
        Move moveA0 = new Move(0, 0);
        game.play(moveA0, Game.PLAYER_1);
        game.play(moveA0, Game.PLAYER_2);
    }
}

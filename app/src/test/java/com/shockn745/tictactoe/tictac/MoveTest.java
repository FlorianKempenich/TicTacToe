package com.shockn745.tictactoe.tictac;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MoveTest {

    @Test
    public void newConstructorHappyPath() throws Exception {
        Move move = new Move(0, 2, Move.PLAYER_1);

        assertEquals(0, move.x);
        assertEquals(2, move.y);
        assertEquals(Move.PLAYER_1, move.player);

    }

    @Test
    public void invalidMove_throwException() throws Exception {
     try {
         new Move(22, 34, Move.PLAYER_1);
         fail();
     } catch (InvalidMoveException e) {
         assertEquals("Out of bounds coordinates", e.getMessage());
     }
    }

    @Test
    public void testEqualityOnMoves() throws Exception {
        assertEquals(new Move(0,0, Move.PLAYER_1), new Move(0,0, Move.PLAYER_1));
        assertNotEquals(new Move(0,0, Move.PLAYER_1), new Move(0,0, Move.PLAYER_2));
        assertNotEquals(new Move(1,0, Move.PLAYER_1), new Move(0,0, Move.PLAYER_1));
        assertNotEquals(new Move(0,1, Move.PLAYER_1), new Move(0,0, Move.PLAYER_1));
    }

    @Test
    public void invalidPlayer_throwException() throws Exception {
        try {
            new Move(1,2, 3);
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Invalid player, USE STATIC FIELDS", e.getMessage());
        }
    }

    @Test
    public void testIf2MovesHaveSameCoordinates() throws Exception {
        Move first = new Move(1,2, Move.PLAYER_1);
        Move second = new Move(1,2, Move.PLAYER_2);

        assertTrue(first.sameCoordinates(second));
    }
}

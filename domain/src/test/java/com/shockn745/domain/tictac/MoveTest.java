package com.shockn745.domain.tictac;

import com.shockn745.domain.exceptions.InvalidMoveException;

import com.shockn745.domain.tictac.Move;
import com.shockn745.domain.tictac.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MoveTest {

    @Test
    public void testHappyPath() throws Exception {
        Move move = new Move(0, 2, Player.player1());

        assertEquals(0, move.x);
        assertEquals(2, move.y);
        assertEquals(Player.player1(), move.player);

    }

    @Test
    public void invalidMove_throwException() throws Exception {
        try {
            new Move(22, 34, Player.player1());
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Out of bounds coordinates", e.getMessage());
        }
    }

    @Test
    public void testEqualityOnMoves() throws Exception {
        assertEquals(new Move(0, 0, Player.player1()), new Move(0, 0, Player.player1()));
        assertNotEquals(new Move(0, 0, Player.player1()), new Move(0, 0, Player.player2()));
        assertNotEquals(new Move(1, 0, Player.player1()), new Move(0, 0, Player.player1()));
        assertNotEquals(new Move(0, 1, Player.player1()), new Move(0, 0, Player.player1()));
    }


    @Test
    public void nullPlayer_throwInvalidMoveException() throws Exception {
        try {
            new Move(1, 2, null);
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Null player, INITIALIZE PLAYER", e.getMessage());
        }

    }

    @Test
    public void testIf2MovesHaveSameCoordinates() throws Exception {
        Move first = new Move(1, 2, Player.player1());
        Move second = new Move(1, 2, Player.player2());

        assertTrue(first.sameCoordinates(second));
    }

    @Test
    public void playWithNoPlayer_throwIllegalMoveException() throws Exception {
        try {
            new Move(1, 2, Player.noPlayer());
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Invalid player. Play only with PLAYER 1 OR PLAYER 2", e.getMessage());
        }

    }

}

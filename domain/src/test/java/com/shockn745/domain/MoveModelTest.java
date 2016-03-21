package com.shockn745.domain;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.InvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MoveModelTest {

    @Test
    public void testHappyPath() throws Exception {
        MoveModel move = new MoveModel(0, 2, Player.player1());

        assertEquals(0, move.x);
        assertEquals(2, move.y);
        assertEquals(Player.player1(), move.player);

    }

    @Test
    public void invalidMove_throwException() throws Exception {
        try {
            new MoveModel(22, 34, Player.player1());
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Out of bounds coordinates", e.getMessage());
        }
    }

    @Test
    public void testEqualityOnMoves() throws Exception {
        assertEquals(new MoveModel(0, 0, Player.player1()), new MoveModel(0, 0, Player.player1()));
        assertNotEquals(new MoveModel(0, 0, Player.player1()), new MoveModel(0, 0, Player.player2()));
        assertNotEquals(new MoveModel(1, 0, Player.player1()), new MoveModel(0, 0, Player.player1()));
        assertNotEquals(new MoveModel(0, 1, Player.player1()), new MoveModel(0, 0, Player.player1()));
    }


    @Test
    public void nullPlayer_throwInvalidMoveException() throws Exception {
        try {
            new MoveModel(1, 2, null);
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Null player, INITIALIZE PLAYER", e.getMessage());
        }

    }

    @Test
    public void testIf2MovesHaveSameCoordinates() throws Exception {
        MoveModel first = new MoveModel(1, 2, Player.player1());
        MoveModel second = new MoveModel(1, 2, Player.player2());

        assertTrue(first.sameCoordinates(second));
    }

    @Test
    public void playWithNoPlayer_throwIllegalMoveException() throws Exception {
        try {
            new MoveModel(1, 2, Player.noPlayer());
            fail();
        } catch (InvalidMoveException e) {
            assertEquals("Invalid player. Play only with PLAYER 1 OR PLAYER 2", e.getMessage());
        }

    }

}

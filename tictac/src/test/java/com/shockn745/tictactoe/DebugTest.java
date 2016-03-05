package com.shockn745.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DebugTest {

    @Test
    public void typePerson_equalsTypeInitializedWith() throws Exception {
        Player player1 = new Player(Player.PLAYER_1);
        assertEquals(Player.PLAYER_1, player1.type);
    }

    @Test
    public void typePerson_equalsTypeInitializedWith_usingMove() throws Exception {
        Move movePlayer1 = new Move(1, 1, Player.PLAYER_1);
        assertEquals(Player.PLAYER_1, movePlayer1.playerOld);
        assertEquals(Player.PLAYER_1, movePlayer1.player.type);
    }
}

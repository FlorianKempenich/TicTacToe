package com.shockn745.application;

import com.shockn745.domain.Player;

/**
 * @author Kempenich Florian
 */
public class Move {
    public final int x;
    public final int y;

    public final Player player;

    public Move(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }
}

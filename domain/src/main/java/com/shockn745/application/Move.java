package com.shockn745.application;

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

    @Override
    public String toString() {
        return "Move: x=" + x + " y=" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (x != move.x) return false;
        if (y != move.y) return false;
        return player != null ? player.equals(move.player) : move.player == null;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}

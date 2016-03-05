package com.shockn745.tictactoe.other;

public class DebugHelper {

    public static void printBoard(int[][] board) {
        System.out.println(getString(board));
    }

    public static String getString(int[][] board) {
        return
                "   0   1   2\n" +
                "0  " + board[0][0] + "   " + board[1][0] + "   " + board[2][0] + "   \n" +
                "0  " + board[0][1] + "   " + board[1][1] + "   " + board[2][1] + "   \n" +
                "0  " + board[0][2] + "   " + board[1][2] + "   " + board[2][2] + "   \n" ;

    }
}

package com.shockn745.tictactoe.iterator;

import com.shockn745.tictactoe.Board;
import com.shockn745.tictactoe.Move;
import com.shockn745.tictactoe.Player;
import com.shockn745.tictactoe.exceptions.IllegalMoveException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardIteratorTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        initBoard(board);
    }

    private static void initBoard(Board board) throws IllegalMoveException {
        board.addMove(new Move(0, 0, Player.player1()));
        board.addMove(new Move(1, 0, Player.player2()));
        board.addMove(new Move(2, 0, Player.player2()));
        board.addMove(new Move(0, 1, Player.player1()));
        board.addMove(new Move(1, 1, Player.player1()));
        board.addMove(new Move(2, 1, Player.player1()));
        board.addMove(new Move(0, 2, Player.player2()));
        board.addMove(new Move(1, 2, Player.player1()));
        board.addMove(new Move(2, 2, Player.player2()));
//        System.out.println(board);
    }

    @Test
    public void iterateInALine() throws Exception {
        int lineIndex = 0;
        BoardIterator lineIterator = new LineIterator(board, lineIndex);

        assertTrue(lineIterator.hasNext());
        assertEquals(Player.player1(), lineIterator.next());

        assertTrue(lineIterator.hasNext());
        assertEquals(Player.player2(), lineIterator.next());

        assertTrue(lineIterator.hasNext());
        assertEquals(Player.player2(), lineIterator.next());
    }

    @Test
    public void iterateInAColumn() throws Exception {
        int lineIndex = 1;
        BoardIterator columnIterator = new ColumnIterator(board, lineIndex);

        assertTrue(columnIterator.hasNext());
        assertEquals(Player.player2(), columnIterator.next());

        assertTrue(columnIterator.hasNext());
        assertEquals(Player.player1(), columnIterator.next());

        assertTrue(columnIterator.hasNext());
        assertEquals(Player.player1(), columnIterator.next());
    }

    @Test
    public void iterate_firstDiagonal() throws Exception {
        BoardIterator firstDiagonalIterator = new FirstDiagonalIterator(board);

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(Player.player1(), firstDiagonalIterator.next());

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(Player.player1(), firstDiagonalIterator.next());

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(Player.player2(), firstDiagonalIterator.next());
    }

    @Test
    public void iterate_secondDiagonal() throws Exception {
        BoardIterator secondDiagonalIterator = new SecondDiagonalIterator(board);

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(Player.player2(), secondDiagonalIterator.next());

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(Player.player1(), secondDiagonalIterator.next());

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(Player.player2(), secondDiagonalIterator.next());
    }
}

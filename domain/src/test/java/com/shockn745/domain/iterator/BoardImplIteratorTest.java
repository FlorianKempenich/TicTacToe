package com.shockn745.domain.iterator;

import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardImplIteratorTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new BoardImpl();
        initBoard(board);
    }

    private static void initBoard(Board board) throws IllegalMoveException {
        board.addMove(new MoveModel(0, 0, Player.player1()));
        board.addMove(new MoveModel(1, 0, Player.player2()));
        board.addMove(new MoveModel(2, 0, Player.player2()));
        board.addMove(new MoveModel(0, 1, Player.player1()));
        board.addMove(new MoveModel(1, 1, Player.player1()));
        board.addMove(new MoveModel(2, 1, Player.player1()));
        board.addMove(new MoveModel(0, 2, Player.player2()));
        board.addMove(new MoveModel(1, 2, Player.player1()));
        board.addMove(new MoveModel(2, 2, Player.player2()));
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

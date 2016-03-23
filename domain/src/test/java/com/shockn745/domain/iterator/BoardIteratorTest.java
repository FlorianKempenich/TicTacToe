package com.shockn745.domain.iterator;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.Square;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardIteratorTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(NullObjects.makeEmptyBoard());
        initBoard(board);
    }

    private static void initBoard(Board board) throws IllegalMoveException {
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 0), Player.player2()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 0), Player.player2()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 1), Player.player1()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 1), Player.player1()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 1), Player.player1()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 2), Player.player2()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 2), Player.player1()));
        board.addMove(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 2), Player.player2()));
    }

    @Test
    public void iterateInALine() throws Exception {
        int lineIndex = 0;
        BoardIterator lineIterator = new LineIterator(board, lineIndex);

        assertTrue(lineIterator.hasNext());
        assertEquals(makeSquare(0, 0, 1), lineIterator.next());

        assertTrue(lineIterator.hasNext());
        assertEquals(makeSquare(1, 0, 2), lineIterator.next());

        assertTrue(lineIterator.hasNext());
        assertEquals(makeSquare(2, 0, 2), lineIterator.next());
    }

    private static Square makeSquare(int x, int y, int playerNumber) {
        Player player;
        switch (playerNumber) {
            case 1:
                player = Player.player1();
                break;
            case 2:
                player = Player.player2();
                break;
            default:
                player = null;
        }
        return new Square(BoardCoordinatesModel.fromCoordinates(x, y), player);
    }

    @Test
    public void iterateInAColumn() throws Exception {
        int lineIndex = 1;
        BoardIterator columnIterator = new ColumnIterator(board, lineIndex);

        assertTrue(columnIterator.hasNext());
        assertEquals(makeSquare(1, 0, 2), columnIterator.next());

        assertTrue(columnIterator.hasNext());
        assertEquals(makeSquare(1, 1, 1), columnIterator.next());

        assertTrue(columnIterator.hasNext());
        assertEquals(makeSquare(1, 2, 1), columnIterator.next());
    }

    @Test
    public void iterate_firstDiagonal() throws Exception {
        BoardIterator firstDiagonalIterator = new FirstDiagonalIterator(board);

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(makeSquare(0, 0, 1), firstDiagonalIterator.next());

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(makeSquare(1, 1, 1), firstDiagonalIterator.next());

        assertTrue(firstDiagonalIterator.hasNext());
        assertEquals(makeSquare(2, 2, 2), firstDiagonalIterator.next());
    }

    @Test
    public void iterate_secondDiagonal() throws Exception {
        BoardIterator secondDiagonalIterator = new SecondDiagonalIterator(board);

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(makeSquare(0, 2, 2), secondDiagonalIterator.next());

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(makeSquare(1, 1, 1), secondDiagonalIterator.next());

        assertTrue(secondDiagonalIterator.hasNext());
        assertEquals(makeSquare(2, 0, 2), secondDiagonalIterator.next());
    }
}

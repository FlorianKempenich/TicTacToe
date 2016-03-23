package com.shockn745.domain.datamapper;

import com.shockn745.domain.Board;
import com.shockn745.domain.Square;

/**
 * @author Kempenich Florian
 */
public class BoardMapper {
    public Board transform(Square[][] board) {
        return new Board(board);
    }

    public Square[][] transformNew(Board board) {
        return board.getNewBoard();
    }
}

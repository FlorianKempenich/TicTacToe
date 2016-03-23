package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Board;

/**
 * @author Kempenich Florian
 */
public class BoardMapper {
    public Board transform(Player[][] board) {
        return new Board(board);
    }

    public Player[][] transform(Board board) {
        return board.getBoard();
    }
}

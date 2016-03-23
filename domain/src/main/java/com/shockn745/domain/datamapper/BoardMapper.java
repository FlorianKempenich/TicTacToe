package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;

/**
 * @author Kempenich Florian
 */
public class BoardMapper {
    public BoardImpl transform(Player[][] board) {
        return new BoardImpl(board);
    }

    public Player[][] transform(BoardImpl board) {
        return board.getBoard();
    }
}

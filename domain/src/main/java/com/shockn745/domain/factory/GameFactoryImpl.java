package com.shockn745.domain.factory;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Game;
import com.shockn745.domain.Square;
import com.shockn745.utils.NullObjects;

import java.util.HashSet;

/**
 * @author Kempenich Florian
 */
public class GameFactoryImpl implements GameFactory {

    @Override
    public Game newGame() {
        Square[][] emptyBoard = NullObjects.makeEmptyBoard();
        Board board = new Board(emptyBoard);
        return new Game(
                board,
                GameStatus.NO_ID,
                Player.noPlayer(),
                Player.noPlayer(),
                BoardCoordinatesModel.noCoordinates(),
                new HashSet<BoardCoordinatesModel>(3)
        );
    }

}

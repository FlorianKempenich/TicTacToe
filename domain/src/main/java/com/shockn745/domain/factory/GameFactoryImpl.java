package com.shockn745.domain.factory;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Board;
import com.shockn745.domain.Game;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameFactoryImpl implements GameFactory {

    private CoordinatesMapper coordinatesMapper;

    public GameFactoryImpl() {
        coordinatesMapper = new CoordinatesMapper(); //todo put constructor
    }

    @Override
    public Game newGame() {
        GameStatus newGameStatus = NullObjects.makeEmptyGameStatus(GameStatus.NO_ID);

        Board board = new Board(newGameStatus.board);
        return new Game(
                board,
                newGameStatus,
                coordinatesMapper.transform(newGameStatus.lastPlayedSquare)
        );
    }

    @Override
    public Game makeGame(GameStatus status) {
        Board board = new Board(status.board);
        return new Game(board, status, coordinatesMapper.transform(status.lastPlayedSquare));
    }

}

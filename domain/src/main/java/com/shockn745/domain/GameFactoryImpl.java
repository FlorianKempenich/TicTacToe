package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
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
    public Game makeNewGame() {
        GameStatus newGameStatus = NullObjects.makeEmptyGameStatus(GameStatus.NO_ID);

        BoardImpl board = new BoardImpl(newGameStatus.board);
        return new Game(
                board,
                newGameStatus,
                coordinatesMapper.transform(newGameStatus.lastPlayedSquare)
        );
    }

    @Override
    public Game makeGame(GameStatus status) {
        BoardImpl board = new BoardImpl(status.board);
        return new Game(board, status, coordinatesMapper.transform(status.lastPlayedSquare));
    }

}

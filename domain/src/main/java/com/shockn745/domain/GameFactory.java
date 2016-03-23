package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameFactory {

    private CoordinatesMapper coordinatesMapper;

    public GameFactory() {
        coordinatesMapper = new CoordinatesMapper(); //todo put constructor
    }

    public Game makeNewGame() {
        GameStatus newGameStatus = NullObjects.makeEmptyGameStatus(GameStatus.NO_ID);

        Board board = new Board(newGameStatus.board);
        return new Game(
                board,
                newGameStatus,
                coordinatesMapper.transform(newGameStatus.lastPlayedSquare)
        );
    }

    public Game makeGame(GameStatus status) {
        Board board = new Board(status.board);
        return new Game(board, status, coordinatesMapper.transform(status.lastPlayedSquare));
    }

}

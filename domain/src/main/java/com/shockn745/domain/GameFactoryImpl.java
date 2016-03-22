package com.shockn745.domain;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GameFactoryImpl implements GameFactory {

    @Override
    public Game makeNewGame() {
        GameStatus newGameStatus = NullObjects.makeEmptyGameStatus(GameStatus.NO_ID);

        Board board = new BoardImpl(newGameStatus.board);
        return new GameImpl(board, newGameStatus, mapToModel(newGameStatus.lastPlayedSquare));
    }

    @Override
    public Game makeGame(GameStatus status) {
        Board board = new BoardImpl(status.board);
        return new GameImpl(board, status, mapToModel(status.lastPlayedSquare));
    }

    // todo maybe put in a dedicated mapper
    private BoardCoordinatesModel mapToModel(BoardCoordinates coordinates) {
        if (coordinates.equals(BoardCoordinates.noCoordinates())) {
            return BoardCoordinatesModel.noCoordinates();
        } else {
            return new BoardCoordinatesModel(coordinates);
        }
    }
}

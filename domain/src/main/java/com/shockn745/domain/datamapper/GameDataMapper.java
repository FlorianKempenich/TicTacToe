package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;

/**
 * @author Kempenich Florian
 */
public class GameDataMapper {

    private final GameFactory gameFactory;

    public GameDataMapper(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public GameStatus transform(Game game) {

        BoardCoordinatesModel lastSquarePlayed = game.getLastSquarePlayed();

        return new GameStatus(
                game.getGameId(),
                game.getBoard().getBoardStatus(),
                game.getPreviousPlayer(),
                game.getWinner(),
                map(lastSquarePlayed)
        );
    }

    private BoardCoordinates map(BoardCoordinatesModel coordinatesModel) {
        if (coordinatesModel.equals(BoardCoordinatesModel.noCoordinates())) {
            return BoardCoordinates.noCoordinates();
        } else {
            return new BoardCoordinates(coordinatesModel.x, coordinatesModel.y);
        }
    }

    public Game transform(GameStatus gameStatus) {
        return gameFactory.makeGame(gameStatus);
    }

}

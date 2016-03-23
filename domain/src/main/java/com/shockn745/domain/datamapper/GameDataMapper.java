package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;

/**
 * @author Kempenich Florian
 */
public class GameDataMapper {

    private final GameFactory gameFactory;
    private final CoordinatesMapper coordinatesMapper;
    private final BoardMapper boardMapper;

    public GameDataMapper(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
        coordinatesMapper = new CoordinatesMapper();// todo put in constructor
        boardMapper = new BoardMapper();
    }

    public GameStatus transform(Game game) {
        return new GameStatus(
                game.getGameId(),
                boardMapper.transform(game.getBoard()),
                game.getPreviousPlayer(),
                game.getWinner(),
                coordinatesMapper.transform(game.getLastSquarePlayed())
        );
    }


    public Game transform(GameStatus gameStatus) {
        return gameFactory.makeGame(gameStatus);
    }

}

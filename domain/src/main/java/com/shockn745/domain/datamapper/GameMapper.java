package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;

/**
 * @author Kempenich Florian
 */
public class GameMapper {

    private final GameFactory gameFactory;
    private final CoordinatesMapper coordinatesMapper;
    private final BoardMapper boardMapper;

    public GameMapper(
            GameFactory gameFactory,
            CoordinatesMapper coordinatesMapper,
            BoardMapper boardMapper) {
        this.gameFactory = gameFactory;
        this.coordinatesMapper = coordinatesMapper;
        this.boardMapper = boardMapper;
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

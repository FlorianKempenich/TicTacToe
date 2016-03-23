package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Game;

/**
 * @author Kempenich Florian
 */
public class GameMapper {

    private final CoordinatesMapper coordinatesMapper;
    private final BoardMapper boardMapper;

    public GameMapper(
            CoordinatesMapper coordinatesMapper,
            BoardMapper boardMapper) {
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
        Board board = boardMapper.transform(gameStatus.board);
        BoardCoordinatesModel lastPlayedSquare =
                coordinatesMapper.transform(gameStatus.lastPlayedSquare);
        return new Game(
                board,
                gameStatus.gameId,
                gameStatus.lastPlayer,
                gameStatus.winner,
                lastPlayedSquare
        );
    }

}

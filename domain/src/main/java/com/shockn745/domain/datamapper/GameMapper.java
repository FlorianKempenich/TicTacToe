package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Game;

import java.util.HashSet;
import java.util.Set;

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
                boardMapper.transformNew(game.getBoard()),
                game.getPreviousPlayer(),
                game.getWinner(),
                coordinatesMapper.transform(game.getLastSquarePlayed()),
                transformWinningSquaresToDto(game.getWinningSquares())
        );
    }

    private Set<BoardCoordinates> transformWinningSquaresToDto(Set<BoardCoordinatesModel> winningSquares) {
        Set<BoardCoordinates> winningSquaresDto = new HashSet<>(3);
        for (BoardCoordinatesModel coordinatesModel : winningSquares) {
            winningSquaresDto.add(coordinatesMapper.transform(coordinatesModel));
        }
        return winningSquaresDto;
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
                lastPlayedSquare,
                transformWinningSquaresToModel(gameStatus.winningSquares)
        );
    }

    private Set<BoardCoordinatesModel> transformWinningSquaresToModel(Set<BoardCoordinates> winningSquares) {
        Set<BoardCoordinatesModel> winningSquaresDto = new HashSet<>(3);
        for (BoardCoordinates coordinates : winningSquares) {
            winningSquaresDto.add(coordinatesMapper.transform(coordinates));
        }
        return winningSquaresDto;
    }

}

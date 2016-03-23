package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.domain.datamapper.MoveMapper;
import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.factory.MapperFactory;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseImpl implements AddMoveUseCase {

    private final GameStatusRepository gameStatusRepository;
    private final GameMapper gameMapper;
    private final MoveMapper moveMapper;

    public AddMoveUseCaseImpl(
            GameStatusRepository gameStatusRepository,
            MapperFactory mapperFactory) {
        this.gameStatusRepository = gameStatusRepository;
        this.gameMapper = mapperFactory.gameMapper();
        this.moveMapper = mapperFactory.moveMapper();
    }

    @Override
    public void execute(Move move, int gameId, Callback callback) {
        MoveModel moveModel = moveMapper.transform(move);
        if (gameStatusRepository.contains(gameId)) {
            executeWithValidGameId(gameId, callback, moveModel);
        } else {
            callback.onError(new com.shockn745.application.driving.dto.GameError(
                    "Game not found : ID=" + gameId));
        }
    }

    private void executeWithValidGameId(int gameId, Callback callback, MoveModel moveModel) {
        try {
            playMove(gameId, callback, moveModel);
        } catch (IllegalMoveException e) {
            callback.onError(new com.shockn745.application.driving.dto.GameError(e.getMessage()));
        }
    }

    private void playMove(int gameId, Callback callback, MoveModel moveModel)
            throws IllegalMoveException {
        GameStatus gameStatus = gameStatusRepository.getGame(gameId);
        Game game = gameMapper.transform(gameStatus);
        game.play(moveModel);
        game.checkIfFinishedAndUpdateWinStatus();

        GameStatus status = gameMapper.transform(game);
        gameStatusRepository.saveGame(status);
        callback.onSuccess(status);
    }
}

package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.datamapper.GameDataMapper;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseImpl implements AddMoveUseCase {

    private final GameStatusRepository gameStatusRepository;
    private final GameDataMapper gameDataMapper;

    public AddMoveUseCaseImpl(
            GameStatusRepository gameStatusRepository,
            GameDataMapper gameDataMapper) {
        this.gameStatusRepository = gameStatusRepository;
        this.gameDataMapper = gameDataMapper;
    }

    @Override
    public void execute(Move move, int gameId, Callback callback) {
        MoveModel moveModel = new MoveModel(move);
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
        Game game = gameDataMapper.transform(gameStatus);
        game.play(moveModel);
        game.checkIfFinishedAndUpdateWinner();

        GameStatus status = gameDataMapper.transform(game);
        gameStatusRepository.saveGame(status);
        callback.onSuccess(status);
    }
}

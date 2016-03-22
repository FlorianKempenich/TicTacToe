package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseImpl implements AddMoveUseCase {

    private final GameStatusRepository gameStatusRepository;
    private final GameFactory gameFactory;

    public AddMoveUseCaseImpl(
            GameStatusRepository gameStatusRepository, GameFactory gameFactory) {
        this.gameStatusRepository = gameStatusRepository;
        this.gameFactory = gameFactory;
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
        Game game = gameFactory.makeGame(gameStatus);
        game.play(moveModel);
        game.checkIfFinishedAndUpdateWinner();
        gameStatusRepository.saveGame(game.makeStatus());
        callback.onSuccess(game.makeStatus());
    }
}

package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driving.AddMoveUseCase;
import com.shockn745.application.driving.Move;
import com.shockn745.domain.Game;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseImpl implements AddMoveUseCase {

    private final GameRepository gameRepository;

    public AddMoveUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void execute(Move move, int gameId, Callback callback) {
        MoveModel moveModel = new MoveModel(move);
        if (gameRepository.contains(gameId)) {
            executeWithValidGameId(gameId, callback, moveModel);
        } else {
            callback.onError(new GameError("Game not found : ID=" + gameId));
        }
    }

    private void executeWithValidGameId(int gameId, Callback callback, MoveModel moveModel) {
        try {
            playMove(gameId, callback, moveModel);
        } catch (IllegalMoveException e) {
            callback.onError(new GameError(e.getMessage()));
        }
    }

    private void playMove(int gameId, Callback callback, MoveModel moveModel) throws IllegalMoveException {
        Game game = gameRepository.getGame(gameId);
        game.play(moveModel);
        game.checkIfFinishedAndUpdateWinner();
        callback.onSuccess(game.makeStatus(gameId));
    }
}

package com.shockn745.application.implementation;

import com.shockn745.GameRepository;
import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.Move;
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
        Game game = gameRepository.getGame(gameId);

        try {
            game.play(moveModel);
        } catch (IllegalMoveException e) {
            // todo test not happy path
        }

        callback.onSuccess(game.makeStatus(gameId));
    }
}

package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.domain.GameImpl;

/**
 * @author Kempenich Florian
 */
public class InitNewGameUseCaseImpl implements InitNewGameUseCase {

    private final GameRepository gameRepository;
    private final GameStatusRepository gameStatusRepository;
    private final GameFactory factory;

    public InitNewGameUseCaseImpl(
            GameRepository gameRepository,
            GameStatusRepository gameStatusRepository, GameFactory factory) {
        this.gameRepository = gameRepository;
        this.gameStatusRepository = gameStatusRepository;
        this.factory = factory;
    }

    @Override
    public void execute(Callback callback) {
        Game game = factory.makeNewGame();
        int id = gameRepository.saveGame(game);

        GameStatus status = game.makeStatus(id);

        callback.newGameReady(status);
    }
}

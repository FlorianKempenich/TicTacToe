package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.factory.GameFactory;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.domain.factory.MapperFactory;

/**
 * @author Kempenich Florian
 */
public class InitNewGameUseCaseImpl implements InitNewGameUseCase {

    private final GameStatusRepository gameStatusRepository;
    private final GameFactory factory;
    private final GameMapper gameMapper;

    public InitNewGameUseCaseImpl(
            GameStatusRepository gameStatusRepository,
            GameFactory factory, MapperFactory mapperFactory) {
        this.gameStatusRepository = gameStatusRepository;
        this.factory = factory;
        this.gameMapper = mapperFactory.gameMapper();
    }

    @Override
    public void execute(Callback callback) {
        Game game = factory.newGame();
        GameStatus status = gameMapper.transform(game);

        int id = gameStatusRepository.saveGame(status);
        status = status.updateWithId(id);

        callback.newGameReady(status);
    }
}

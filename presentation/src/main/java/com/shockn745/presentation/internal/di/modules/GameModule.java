package com.shockn745.presentation.internal.di.modules;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.driving.implementation.InitNewGameUseCaseImpl;
import com.shockn745.application.driving.implementation.RegisterNetworkGameListenerUseCaseImpl;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.GameDataMapper;
import com.shockn745.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Kempenich Florian
 */
@Module
public class GameModule {

    @Provides
    @PerActivity
    InitNewGameUseCase provideInitNewGameUseCase(
            GameStatusRepository gameStatusRepository,
            GameFactory gameFactory,
            GameDataMapper gameDataMapper) {
        return new InitNewGameUseCaseImpl(gameStatusRepository, gameFactory, gameDataMapper);
    }

    @Provides
    @PerActivity
    AddMoveUseCase provideAddMoveUseCase(
            GameStatusRepository gameStatusRepository,
            GameDataMapper gameDataMapper) {
        return new AddMoveUseCaseImpl(gameStatusRepository, gameDataMapper);
    }

    @Provides
    @PerActivity
    RegisterNetworkGameListenerUseCase provideRegisterNetworkGameListenerUseCase(
            NetworkListenerRepository networkListenerRepository) {
        return new RegisterNetworkGameListenerUseCaseImpl(networkListenerRepository);
    }
}

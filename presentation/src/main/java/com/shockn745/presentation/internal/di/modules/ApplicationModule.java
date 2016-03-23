package com.shockn745.presentation.internal.di.modules;

import android.content.Context;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.data.InMemoryGameStatusRepository;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.network.NetworkListenerRepositoryImpl;
import com.shockn745.presentation.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Kempenich Florian
 */
@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    GameStatusRepository provideGameStatusRepository() {
        return new InMemoryGameStatusRepository();
    }

    @Provides
    @Singleton
    NetworkListenerRepository provideNetworkListenersRepository() {
        return new NetworkListenerRepositoryImpl();
    }

    @Provides
    @Singleton
    GameFactory provideGameFactory() {
        return new GameFactory();
    }

    @Provides
    @Singleton
    GameMapper provideGameDateMapper(GameFactory gameFactory) {
        CoordinatesMapper coordinatesMapper = new CoordinatesMapper();
        BoardMapper boardMapper = new BoardMapper();
        return new GameMapper(gameFactory, coordinatesMapper, boardMapper);
    }

}

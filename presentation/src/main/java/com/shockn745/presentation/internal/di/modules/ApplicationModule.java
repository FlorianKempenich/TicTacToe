package com.shockn745.presentation.internal.di.modules;

import android.content.Context;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.data.InMemoryGameStatusRepository;
import com.shockn745.domain.factory.GameFactory;
import com.shockn745.domain.factory.GameFactoryImpl;
import com.shockn745.domain.factory.MapperFactory;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.domain.factory.MapperFactoryImpl;
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
        return new GameFactoryImpl();
    }

    @Provides
    @Singleton
    MapperFactory provideMapperFactory() {
        return new MapperFactoryImpl();
    }

    @Provides
    @Singleton
    GameMapper provideGameDateMapper(MapperFactory mapperFactory) {
        return mapperFactory.gameMapper();
    }

}

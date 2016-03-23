package com.shockn745.presentation.internal.di.components;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.GameDataMapper;
import com.shockn745.presentation.internal.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Kempenich Florian
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    GameStatusRepository gameStatusRepository();

    NetworkListenerRepository networkListenerRepository();

    GameFactory gameFactory();

    GameDataMapper gameDataMapper();
}

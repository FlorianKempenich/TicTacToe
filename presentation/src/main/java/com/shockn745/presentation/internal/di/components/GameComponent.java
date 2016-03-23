package com.shockn745.presentation.internal.di.components;

import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.presentation.internal.di.PerActivity;
import com.shockn745.presentation.internal.di.modules.ApplicationModule;
import com.shockn745.presentation.internal.di.modules.GameModule;

import dagger.Component;

/**
 * @author Kempenich Florian
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = GameModule.class)
public interface GameComponent {
    InitNewGameUseCase initNewGameUseCase();
    AddMoveUseCase addMoveUseCase();
    RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase();
}

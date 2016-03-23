package com.shockn745.presentation.internal.di.components;

import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.game.GameAnimations;
import com.shockn745.presentation.game.GameContract;
import com.shockn745.presentation.internal.di.PerActivity;
import com.shockn745.presentation.internal.di.modules.UseCasesModule;
import com.shockn745.presentation.internal.di.modules.GameActivityModule;

import dagger.Component;

/**
 * @author Kempenich Florian
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        GameActivityModule.class,
        UseCasesModule.class
})
public interface GameActivityComponent {
    void inject(GameActivity gameActivity);
}

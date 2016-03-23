package com.shockn745.presentation.internal.di.components;

import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.internal.di.PerActivity;
import com.shockn745.presentation.internal.di.modules.GameActivityModule;
import com.shockn745.presentation.internal.di.modules.UseCasesModule;
import com.shockn745.presentation.internal.di.modules.WinActivityModule;
import com.shockn745.presentation.win.WinActivity;

import dagger.Component;

/**
 * @author Kempenich Florian
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        WinActivityModule.class,
        UseCasesModule.class
})
public interface WinActivityComponent {
    void inject(WinActivity gameActivity);
}

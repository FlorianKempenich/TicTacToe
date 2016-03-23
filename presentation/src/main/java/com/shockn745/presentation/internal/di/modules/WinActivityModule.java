package com.shockn745.presentation.internal.di.modules;

import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.game.GameContract;
import com.shockn745.presentation.game.GamePresenter;
import com.shockn745.presentation.internal.di.PerActivity;
import com.shockn745.presentation.win.WinActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Kempenich Florian
 */
@Module
public class WinActivityModule {

    private final WinActivity activity;
//    private final GameContract.View view;

    public WinActivityModule(WinActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    WinActivity provideWinActivity() {
        return activity;
    }


}

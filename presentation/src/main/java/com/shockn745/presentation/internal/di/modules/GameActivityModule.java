package com.shockn745.presentation.internal.di.modules;

import android.app.Activity;

import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.game.GameContract;
import com.shockn745.presentation.game.GamePresenter;
import com.shockn745.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Kempenich Florian
 */
@Module
public class GameActivityModule {

    private final GameActivity activity;
    private final GameContract.View view;

    public GameActivityModule(GameContract.View view, GameActivity activity) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    GameActivity provideGameActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    GameContract.View provideGameView() {
        return view;
    }

    @Provides
    @PerActivity
    GameContract.Presenter provideGamePresenter(GamePresenter presenter) {
        return presenter;
    }

}

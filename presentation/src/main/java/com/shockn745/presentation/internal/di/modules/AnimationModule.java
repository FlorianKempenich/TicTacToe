package com.shockn745.presentation.internal.di.modules;

import android.app.Activity;

import com.shockn745.presentation.game.GameAnimations;
import com.shockn745.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Kempenich Florian
 */
@Module
public class AnimationModule {

    private final Activity activity;

    public AnimationModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    GameAnimations provideGameAnimations() {
        return new GameAnimations(activity);
    }
}

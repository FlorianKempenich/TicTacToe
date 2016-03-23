package com.shockn745.presentation.internal.di.components;

import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.game.GameAnimations;
import com.shockn745.presentation.internal.di.PerActivity;
import com.shockn745.presentation.internal.di.modules.AnimationModule;

import dagger.Component;

/**
 * @author Kempenich Florian
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = AnimationModule.class)
public interface AnimationComponent {

    GameAnimations gameAnimations();
}

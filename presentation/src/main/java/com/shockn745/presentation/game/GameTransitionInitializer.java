package com.shockn745.presentation.game;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.Window;

import com.shockn745.domain.R;

/**
 * @author Kempenich Florian
 */
public class GameTransitionInitializer {

    public void initTransitions(GameActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initTransitionsAfterLollipop(activity);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransitionsAfterLollipop(GameActivity activity) {
        activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        activity.getWindow().setSharedElementsUseOverlay(true);
        activity.getWindow().setEnterTransition(makeEnterTransition());
        activity.getWindow().setExitTransition(makeExitTransition());
        activity.getWindow().setReturnTransition(null);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Transition makeEnterTransition() {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(1500);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        slide.excludeTarget(R.id.main_toolbar, true);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        return slide;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Transition makeExitTransition() {
        Explode explode = new Explode();
        explode.setDuration(1500);
        explode.excludeTarget(android.R.id.statusBarBackground, true);
        explode.excludeTarget(android.R.id.navigationBarBackground, true);
        explode.excludeTarget(R.id.main_toolbar, true);
        explode.setInterpolator(new FastOutSlowInInterpolator());
        return explode;
    }
}

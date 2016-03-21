package com.shockn745.presentation.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import com.shockn745.domain.R;

/**
 * @author Kempenich Florian
 */
public class GameAnimations {

    private View firstPlayerBackground;
    private View secondPlayerBackground;

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
        excludeTargets(slide);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        Fade fade = new Fade(Fade.IN);
        fade.addTarget(R.id.game_first_player_background);
        fade.addTarget(R.id.game_second_player_background);

        TransitionSet set = new TransitionSet();
        set.addTransition(slide);
        set.addTransition(fade);
        return set;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void excludeTargets(Transition transition) {
        transition.excludeTarget(android.R.id.statusBarBackground, true);
        transition.excludeTarget(android.R.id.navigationBarBackground, true);
        transition.excludeTarget(R.id.game_toolbar, true);
        transition.excludeTarget(R.id.game_first_player_background, true);
        transition.excludeTarget(R.id.game_second_player_background, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Transition makeExitTransition() {
        Explode explode = new Explode();
        explode.setDuration(1500);
        excludeTargets(explode);
        explode.setInterpolator(new FastOutSlowInInterpolator());
        return explode;
    }

    public void setPlayerBackgrounds(View firstPlayerBackground, View secondPlayerBackground) {
        this.firstPlayerBackground = firstPlayerBackground;
        this.secondPlayerBackground = secondPlayerBackground;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToFirstPlayerBackground() {
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.GONE);
        if (firstPlayerBackground.isAttachedToWindow()) {
            Animator reveal = makeCircularReveal(firstPlayerBackground);
            reveal.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Animator makeCircularReveal(View myView) {
        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        return ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToSecondPlayerBackground() {
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.VISIBLE);
        if (firstPlayerBackground.isAttachedToWindow()) {
            Animator reveal = makeCircularReveal(secondPlayerBackground);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    firstPlayerBackground.setVisibility(View.INVISIBLE);
                }
            });
            reveal.start();
        }
    }

    private void checkIfBackgroundsInit() {
        if (firstPlayerBackground == null || secondPlayerBackground == null) {
            throw new IllegalStateException("Do not forget to initialize player backgrounds");
        }
    }
}

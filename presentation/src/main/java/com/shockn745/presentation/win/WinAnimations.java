package com.shockn745.presentation.win;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import com.shockn745.domain.R;
import com.shockn745.presentation.game.GameActivity;
import com.shockn745.presentation.game.TicTacView;

import javax.inject.Inject;

/**
 * @author Kempenich Florian
 */
public class WinAnimations {

    private View firstPlayerBackground;
    private View secondPlayerBackground;

    private final WinActivity activity;

    @Inject
    public WinAnimations(WinActivity activity) {
        this.activity = activity;
    }

    public void initTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initTransitionsAfterLollipop();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransitionsAfterLollipop() {
        activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        activity.getWindow().setEnterTransition(makeEnterTransition());
        activity.getWindow().setExitTransition(makeExitTransition());
        activity.getWindow().setReturnTransition(null);
        activity.getWindow().setTransitionBackgroundFadeDuration(1000);
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
        transition.excludeTarget(R.id.win_toolbar, true);
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
    public void transitionToFirstPlayerBackground(TicTacView.ClickCoordinates coordinates) {
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.GONE);
        if (firstPlayerBackground.isAttachedToWindow()) {
            Animator reveal = makeCircularReveal(firstPlayerBackground, coordinates);
            reveal.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Animator makeCircularReveal(View view, TicTacView.ClickCoordinates coordinates) {
        // get the center for the clipping circle (center of the button)
        int cx = (int) coordinates.x;
        int cy = (int) coordinates.y;

        // Make end radius depending on screen size
        float startRadius = computeStartRadius(coordinates);
        float endRadius = computeEndRadius();
        // Make start radius depending on square size

        Animator reveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, endRadius);
        reveal.setDuration(700);
        return reveal;
    }

    private float computeStartRadius(TicTacView.ClickCoordinates coordinates) {
        View clickedView = coordinates.clickedView;
        int[] locationOnScreen = new int[2];
        clickedView.getLocationOnScreen(locationOnScreen);

        float distanceToLeftSide = coordinates.x - locationOnScreen[0];
        float distanceToRightSide = locationOnScreen[0] + clickedView.getWidth() - coordinates.x;
        float distanceToTop = coordinates.y - locationOnScreen[1];
        float distanceToBottom = locationOnScreen[1] + clickedView.getHeight() - coordinates.y;

        float minHorizontal = Math.min(distanceToLeftSide, distanceToRightSide);
        float minVertical = Math.min(distanceToBottom, distanceToTop);

        return Math.min(minHorizontal, minVertical);
    }

    private float computeEndRadius() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return (float) Math.hypot(screenSize.x, screenSize.y);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToSecondPlayerBackground(TicTacView.ClickCoordinates coordinates) {
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.VISIBLE);
        if (firstPlayerBackground.isAttachedToWindow()) {
            Animator reveal = makeCircularReveal(secondPlayerBackground, coordinates);
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

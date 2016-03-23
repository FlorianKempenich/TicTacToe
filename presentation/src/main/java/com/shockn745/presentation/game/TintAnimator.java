package com.shockn745.presentation.game;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

/**
 * @author Kempenich Florian
 */
public class TintAnimator {

    private final int fromColor;
    private final int toColor;

    public TintAnimator(@ColorRes int fromColor, @ColorRes int toColor, Context context) {
        this.fromColor = ContextCompat.getColor(context, fromColor);
        this.toColor= ContextCompat.getColor(context, toColor);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateTint(final View view) {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();
                int blended = blendColors(fromColor, toColor, position);

                // Apply blended color to the view.
                view.setBackgroundTintList(ColorStateList.valueOf(blended));
            }
        });

//        anim.setRepeatCount(1);
        anim.setDuration(400);
//        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setInterpolator(new LinearOutSlowInInterpolator());
        anim.start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }
}

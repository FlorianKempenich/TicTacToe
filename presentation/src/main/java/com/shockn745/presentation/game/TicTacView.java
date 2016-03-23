package com.shockn745.presentation.game;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.domain.R;

import java.util.Set;

/**
 * @author Kempenich Florian
 */
public class TicTacView extends LinearLayout implements View.OnClickListener {

    AppCompatButton[][] buttons = new AppCompatButton[3][3];
    private OnSquareClickedListener listener;

    public TicTacView(Context context) {
        this(context, null);
    }

    public TicTacView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicTacView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        setOrientation(VERTICAL);
        initButtons();
        addLine(0);
        addLine(1);
        addLine(2);
    }

    private void initButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                AppCompatButton button = makeNewSquare();
                button.setOnClickListener(this);
                buttons[i][j] = button;
            }
        }
    }

    private AppCompatButton makeNewSquare() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        AppCompatButton button = (AppCompatButton) inflater.inflate(
                R.layout.tic_tac_view_square,
                new LinearLayout(getContext()), // used to generate the layout params
                false
        );
        int id = View.generateViewId();
        button.setId(id);
        return button;
    }

    private void addLine(int lineIndex) {
        LinearLayout line = new LinearLayout(getContext());
        for (int i = 0; i < 3; i++) {
            Button button = buttons[i][lineIndex];
            line.addView(button, makeSquareLayoutParams());
        }
        addView(line, makeLineLayoutParams());
    }

    private LayoutParams makeSquareLayoutParams() {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        return params;
    }

    private LayoutParams makeLineLayoutParams() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        params.weight = 1;
        return params;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        redirectClick(id);
    }

    private void redirectClick(int id) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (id == buttons[i][j].getId()) {
                    onSquareClick(i, j);
                }
            }
        }
    }

    public ClickCoordinates getSquareCenter(BoardCoordinates coordinates) {
        return getButtonCenter(buttons[coordinates.x][coordinates.y]);
    }

    private ClickCoordinates getButtonCenter(Button button) {

        int[] locationOnScreen = new int[2];
        button.getLocationOnScreen(locationOnScreen);
        float x = locationOnScreen[0] + ((float) button.getWidth())  / 2.0f;
        float y = locationOnScreen[1] + ((float) button.getHeight()) / 4.0f; // Haven't quite figured out WHY?? but it works and is only cosmetic
        return new ClickCoordinates(x, y, button);
    }

    private void onSquareClick(int x, int y) {
        if (listener != null) {
            listener.onSquareClicked(x, y);
        }
    }

    public void setListener(OnSquareClickedListener listener) {
        this.listener = listener;
    }

    public void setSquareText(String text, int x, int y) {
        buttons[x][y].setText(text);
    }

    public void animateWinningSquares(Set<BoardCoordinates> winningSquares) {
        for (BoardCoordinates coordinates: winningSquares) {
            tryToAnimateWinningSquare(coordinates);
        }
    }

    private void tryToAnimateWinningSquare(BoardCoordinates coordinates) {
        try {
            Button toAnimate = buttons[coordinates.x][coordinates.y];
            animateWinningSquare(toAnimate);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void animateWinningSquare(final Button toAnimate) {
        TintAnimator tintAnimator = new TintAnimator(R.color.white, R.color.win, getContext());
        tintAnimator.animateTint(toAnimate);
    }

    public interface OnSquareClickedListener {
        void onSquareClicked(int x, int y);
    }

    public static class ClickCoordinates {
        public final float x;
        public final float y;
        public final View clickedView;

        public ClickCoordinates(float x, float y, View clickedView) {
            this.x = x;
            this.y = y;
            this.clickedView = clickedView;
        }
    }
}

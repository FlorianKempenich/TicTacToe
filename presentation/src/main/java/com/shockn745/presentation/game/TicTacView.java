package com.shockn745.presentation.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author Kempenich Florian
 */
public class TicTacView extends LinearLayout implements View.OnClickListener {

    Button[][] buttons = new Button[3][3];
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
                Button button = makeNewSquare();
                button.setOnClickListener(this);
                buttons[i][j] = button;
            }
        }
    }

    private Button makeNewSquare() {
        Button button = new Button(getContext());
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
        parseCoordinatesAndRedirect(id);
    }

    private void parseCoordinatesAndRedirect(int id) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (id == buttons[i][j].getId()) {
                    onSquareClick(i, j);
                }
            }
        }
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

    public interface OnSquareClickedListener {
        void onSquareClicked(int x, int y);
    }
}

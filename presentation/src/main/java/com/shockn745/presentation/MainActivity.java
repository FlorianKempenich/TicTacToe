package com.shockn745.presentation;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.data.InMemoryGameRepository;
import com.shockn745.GameRepository;
import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.application.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.implementation.InitNewGameUseCaseImpl;
import com.shockn745.domain.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Bind(R.id.button00)
    Button button00;
    @Bind(R.id.button10)
    Button button10;
    @Bind(R.id.button20)
    Button button20;
    @Bind(R.id.button01)
    Button button01;
    @Bind(R.id.button11)
    Button button11;
    @Bind(R.id.button21)
    Button button21;
    @Bind(R.id.button02)
    Button button02;
    @Bind(R.id.button12)
    Button button12;
    @Bind(R.id.button22)
    Button button22;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        presenter.onCreate();
    }

    private void initPresenter() {
        GameRepository gameRepository = new InMemoryGameRepository();
        InitNewGameUseCase initNewGameUseCase = new InitNewGameUseCaseImpl(gameRepository);
        AddMoveUseCase addMoveUseCase = new AddMoveUseCaseImpl(gameRepository);
        presenter = new MainPresenter(this, initNewGameUseCase, addMoveUseCase);
    }

    @OnClick(R.id.button00)
    void on00Clicked() {
        onClicked(0, 0);
    }

    void onClicked(int x, int y) {
        presenter.onSquareClicked(x, y);
        Snackbar.make(button00, "Button : x=" + x + " y=" + y + " clicked!", Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button10)
    void on10Clicked() {
        onClicked(1, 0);
    }

    @OnClick(R.id.button20)
    void on20Clicked() {
        onClicked(2, 0);
    }

    @OnClick(R.id.button01)
    void on01Clicked() {
        onClicked(0, 1);
    }

    @OnClick(R.id.button11)
    void on11Clicked() {
        onClicked(1, 1);
    }

    @OnClick(R.id.button21)
    void on21Clicked() {
        onClicked(2, 1);
    }

    @OnClick(R.id.button02)
    void on02Clicked() {
        onClicked(0, 2);
    }

    @OnClick(R.id.button12)
    void on12Clicked() {
        onClicked(1, 2);
    }

    @OnClick(R.id.button22)
    void on22Clicked() {
        onClicked(2, 2);
    }

    @Override
    public void setSquareText(String text, int x, int y) {
        if (x == 0 && y == 0)
            button00.setText(text);
    }
}

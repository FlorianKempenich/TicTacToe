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

public class MainActivity extends AppCompatActivity implements MainContract.View, TicTacView.OnSquareClickedListener {

    @Bind(R.id.main_tictac_view)
    TicTacView ticTacView;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ticTacView.setListener(this);

        initPresenter();
        presenter.onCreate();
    }

    private void initPresenter() {
        GameRepository gameRepository = new InMemoryGameRepository();
        InitNewGameUseCase initNewGameUseCase = new InitNewGameUseCaseImpl(gameRepository);
        AddMoveUseCase addMoveUseCase = new AddMoveUseCaseImpl(gameRepository);
        presenter = new MainPresenter(this, initNewGameUseCase, addMoveUseCase);
    }

    @Override
    public void onSquareClicked(int x, int y) {
        presenter.onSquareClicked(x, y);
        Snackbar.make(ticTacView, "Button : x=" + x + " y=" + y + " clicked!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setSquareText(String text, int x, int y) {
        ticTacView.setSquareText(text, x, y);
    }
}

package com.shockn745.presentation.game;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.data.InMemoryGameRepository;
import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driving.AddMoveUseCase;
import com.shockn745.application.driving.InitNewGameUseCase;
import com.shockn745.application.driving.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.driving.implementation.InitNewGameUseCaseImpl;
import com.shockn745.domain.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameContract.View, TicTacView.OnSquareClickedListener {

    @Bind(R.id.main_tictac_view)
    TicTacView ticTacView;

    @Bind(R.id.main_current_player_textview)
    TextView currentPlayer;

    @Bind(R.id.main_winner_textview)
    TextView winner;

    private GameContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GameTransitionInitializer().initTransitions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ticTacView.setListener(this);

        initPresenter();
        presenter.onCreate();
    }

    private void initPresenter() {
        GameRepository gameRepository = new InMemoryGameRepository();
        InitNewGameUseCase initNewGameUseCase = new InitNewGameUseCaseImpl(gameRepository);
        AddMoveUseCase addMoveUseCase = new AddMoveUseCaseImpl(gameRepository);
        presenter = new GamePresenter(this, initNewGameUseCase, addMoveUseCase);
    }

    @Override
    public void onSquareClicked(int x, int y) {
        presenter.onSquareClicked(x, y);
    }

    @Override
    public void setSquareText(String text, int x, int y) {
        ticTacView.setSquareText(text, x, y);
    }

    @Override
    public void setCurrentPlayerName(String text) {
        currentPlayer.setText(text);
    }

    @Override
    public void displayWinner(String winnerName) {
        String winnerText = String.format(getString(R.string.winner), winnerName);
        winner.setText(winnerText);
        winner.setVisibility(View.VISIBLE);
        currentPlayer.setVisibility(View.GONE);
    }

    @OnClick(R.id.main_reset_game_button)
    public void resetGame() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            resetGamePreLollipop();
        } else {
            resetGamePostLollipop();
        }
    }

    private void resetGamePreLollipop() {
        presenter.resetGame();
        resetViewVisibility();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void resetGamePostLollipop() {
        Intent resetActivity = new Intent(this, GameActivity.class);
        startActivity(
                resetActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        );
    }

    private void resetViewVisibility() {
        winner.setVisibility(View.GONE);
        currentPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

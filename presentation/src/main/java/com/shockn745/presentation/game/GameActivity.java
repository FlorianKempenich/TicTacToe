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
import com.shockn745.application.driving.Player;
import com.shockn745.application.driving.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.driving.implementation.InitNewGameUseCaseImpl;
import com.shockn745.domain.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameContract.View, TicTacView.OnSquareClickedListener {

    @Bind(R.id.game_tictac_view)
    TicTacView ticTacView;

    @Bind(R.id.game_current_player_textview)
    TextView currentPlayer;

    @Bind(R.id.game_winner_textview)
    TextView winner;

    @Bind(R.id.game_first_player_background)
    View firstPlayerBackground;
    @Bind(R.id.game_second_player_background)
    View secondPlayerBackground;

    private GameContract.Presenter presenter;
    private GameAnimations gameAnimations;
    private TicTacView.ClickCoordinates clickedCoordinates = new TicTacView.ClickCoordinates(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameAnimations = new GameAnimations(this);
        gameAnimations.initTransitions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gameAnimations.setPlayerBackgrounds(firstPlayerBackground, secondPlayerBackground);

        Toolbar toolbar = (Toolbar) findViewById(R.id.game_toolbar);
        setSupportActionBar(toolbar);
        ticTacView.setListener(this);

        initPresenter();
        presenter.onCreate();

        resetViewVisibility();
    }

    private void initPresenter() {
        GameRepository gameRepository = new InMemoryGameRepository();
        InitNewGameUseCase initNewGameUseCase = new InitNewGameUseCaseImpl(gameRepository);
        AddMoveUseCase addMoveUseCase = new AddMoveUseCaseImpl(gameRepository);
        presenter = new GamePresenter(this, initNewGameUseCase, addMoveUseCase);
    }

    private void resetViewVisibility() {
        winner.setVisibility(View.GONE);
        currentPlayer.setVisibility(View.VISIBLE);
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSquareClicked(int x, int y, TicTacView.ClickCoordinates coordinates) {
        clickedCoordinates = coordinates;
        presenter.onSquareClicked(x, y);
    }

    @Override
    public void setSquareText(String text, int x, int y) {
        ticTacView.setSquareText(text, x, y);
    }

    @Override
    public void setCurrentPlayer(Player player) {
        if (player.equals(Player.player1())) {
            gameAnimations.transitionToFirstPlayerBackground(clickedCoordinates);
        } else {
            gameAnimations.transitionToSecondPlayerBackground(clickedCoordinates);
        }
        String playerName = presenter.getPlayerName(player);
        currentPlayer.setText(playerName);
    }

    @Override
    public void displayWinner(String winnerName) {
        String winnerText = String.format(getString(R.string.winner), winnerName);
        winner.setText(winnerText);
        winner.setVisibility(View.VISIBLE);
        currentPlayer.setVisibility(View.GONE);
    }

    @OnClick(R.id.game_reset_game_button)
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

    @Override
    public void onBackPressed() {
        finish();
    }
}

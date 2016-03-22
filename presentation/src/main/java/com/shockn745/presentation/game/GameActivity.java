package com.shockn745.presentation.game;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.driving.implementation.InitNewGameUseCaseImpl;
import com.shockn745.application.driving.implementation.RegisterNetworkGameListenerUseCaseImpl;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.data.InMemoryGameStatusRepository;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.domain.R;
import com.shockn745.network.NetworkListenerRepositoryImpl;
import com.shockn745.presentation.other.FakeMoveFromNetworkGenerator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity
        implements GameContract.View, TicTacView.OnSquareClickedListener {

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
    private FakeMoveFromNetworkGenerator fakeMoveFromNetworkGenerator;

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

    /**
     * Replace with dependency injection
     */
    private void initPresenter() {
        GameStatusRepository gameStatusRepository = new InMemoryGameStatusRepository();
        NetworkListenerRepository networkListenerRepository = new NetworkListenerRepositoryImpl();
        GameFactory gameFactory = new GameFactoryImpl();
        InitNewGameUseCase initNewGameUseCase =
                new InitNewGameUseCaseImpl(gameStatusRepository, gameFactory);
        AddMoveUseCase addMoveUseCase = new AddMoveUseCaseImpl(gameStatusRepository, gameFactory);
        RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase =
                new RegisterNetworkGameListenerUseCaseImpl(networkListenerRepository);
        fakeMoveFromNetworkGenerator =
                new FakeMoveFromNetworkGenerator(
                        gameStatusRepository,
                        gameFactory,
                        networkListenerRepository
                );
        presenter = new GamePresenter(this, initNewGameUseCase,
                registerNetworkGameListenerUseCase, addMoveUseCase
        );
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

    @Override
    public void displayDebugSnackbar(String message) {
        Snackbar.make(ticTacView, "Debug : " + message, Snackbar.LENGTH_SHORT).show();
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

    @OnClick(R.id.game_fake_network_move_button)
    public void makeFakeNetworkMove() {
        fakeMoveFromNetworkGenerator.makeFakeMoveFromNetwork(
                new Move(0, 0, Player.player1()),
                presenter.getGameId(),
                new AddMoveFromNetworkUseCase.Callback() {
                    @Override
                    public void onError(GameError e) {
                        displayDebugSnackbar(e.reason);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

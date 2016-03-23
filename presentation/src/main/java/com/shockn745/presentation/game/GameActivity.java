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

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.R;
import com.shockn745.presentation.AndroidApplication;
import com.shockn745.presentation.internal.di.components.ApplicationComponent;
import com.shockn745.presentation.internal.di.components.DaggerGameActivityComponent;
import com.shockn745.presentation.internal.di.components.GameActivityComponent;
import com.shockn745.presentation.internal.di.modules.GameActivityModule;
import com.shockn745.presentation.internal.di.modules.UseCasesModule;
import com.shockn745.presentation.other.FakeMoveFromNetworkGenerator;

import javax.inject.Inject;

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

    @Inject
    GameContract.Presenter presenter;
    @Inject
    GameAnimations gameAnimations;
    private FakeMoveFromNetworkGenerator fakeMoveFromNetworkGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectActivity();
        // Init members before view
        gameAnimations.initTransitions();

        // Init view
        super.onCreate(savedInstanceState);
        initView();

        // Init members after view
        ticTacView.setListener(this);
        gameAnimations.setPlayerBackgrounds(firstPlayerBackground, secondPlayerBackground);
        presenter.onCreate();

        resetViewVisibility();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.game_toolbar);
        setSupportActionBar(toolbar);
    }

    private void injectActivity() {
        GameActivityComponent gameActivityComponent = getGameActivityComponent();

        gameActivityComponent.inject(this);
        initFakeMoveFromNetworkGenerator();
    }

    private GameActivityComponent getGameActivityComponent() {
        ApplicationComponent appComponent = AndroidApplication.getApplicationComponent(this);
        return DaggerGameActivityComponent.builder()
                .applicationComponent(appComponent)
                .gameActivityModule(new GameActivityModule(this, this))
                .useCasesModule(new UseCasesModule())
                .build();
    }

    private void initFakeMoveFromNetworkGenerator() {
        ApplicationComponent appComponent = AndroidApplication.getApplicationComponent(this);
        fakeMoveFromNetworkGenerator =
                new FakeMoveFromNetworkGenerator(
                        appComponent.gameStatusRepository(),
                        appComponent.networkListenerRepository()
                );
    }

    private void resetViewVisibility() {
        winner.setVisibility(View.GONE);
        currentPlayer.setVisibility(View.VISIBLE);
        firstPlayerBackground.setVisibility(View.VISIBLE);
        secondPlayerBackground.setVisibility(View.INVISIBLE);
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
    public void setCurrentPlayer(Player player, BoardCoordinates lastSquarePlayed) {
        if (isLastSquarePlayedAvailable(lastSquarePlayed)) {
            animateToPlayerBackground(player, lastSquarePlayed);
        }
        String playerName = presenter.getPlayerName(player);
        currentPlayer.setText(playerName);
    }

    private void animateToPlayerBackground(Player player, BoardCoordinates lastSquarePlayed) {
        TicTacView.ClickCoordinates clickCoordinates =
                ticTacView.getSquareCenter(lastSquarePlayed);
        if (player.equals(Player.player1())) {
            gameAnimations.transitionToFirstPlayerBackground(clickCoordinates);
        } else {
            gameAnimations.transitionToSecondPlayerBackground(clickCoordinates);
        }
    }

    private boolean isLastSquarePlayedAvailable(BoardCoordinates lastSquarePlayer) {
        return !lastSquarePlayer.equals(BoardCoordinates.noCoordinates());
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
                new Move(new BoardCoordinates(1,2), Player.player1()),
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

package com.shockn745.presentation.game;

import android.support.annotation.NonNull;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.utils.NullObjects;

import javax.inject.Inject;

/**
 * @author Kempenich Florian
 */
public class GamePresenter implements GameContract.Presenter {
    private final GameContract.View view;

    private final InitNewGameUseCase initNewGameUseCase;
    private final AddMoveUseCase addMoveUseCase;
    private final RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase;

    private GameStatus currentGameStatus = NullObjects.makeEmptyGameStatus(-1);

    private final AddMoveUseCase.Callback addMoveCallback = new AddMoveUseCase.Callback() {
        @Override
        public void onSuccess(GameStatus status) {
            onGameStatusReceived(status);
        }

        @Override
        public void onError(GameError error) {
            onAddMoveError(error);
        }
    };
    private final NetworkListenerRepository.GameNetworkListener gameNetworkListener =
            new NetworkListenerRepository.GameNetworkListener() {
                @Override
                public void onNewMoveFromNetwork(GameStatus status) {
                    onGameStatusReceived(status);
                }
            };
    private final InitNewGameUseCase.Callback initCallback = new InitNewGameUseCase.Callback() {
        @Override
        public void newGameReady(GameStatus newGame) {
            onGameStatusReceived(newGame);
            registerNetworkListener();
        }
    };

    @Inject
    public GamePresenter(GameContract.View view,  InitNewGameUseCase initNewGameUseCase,
            RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase,
            AddMoveUseCase addMoveUseCase) {
        this.view = view;
        this.initNewGameUseCase = initNewGameUseCase;
        this.addMoveUseCase = addMoveUseCase;
        this.registerNetworkGameListenerUseCase = registerNetworkGameListenerUseCase;
    }

    @Override
    public void onCreate() {
        initNewGameUseCase.execute(initCallback);
    }

    @Override
    public void resetGame() {
        initNewGameUseCase.execute(initCallback);
    }

    @Override
    public void onSquareClicked(int x, int y) {
        if (shouldPlayMove(x, y)) {
            Player currentPlayer = getCurrentPlayer();
            addMoveUseCase.execute(
                    new Move(x, y, currentPlayer),
                    currentGameStatus.gameId,
                    addMoveCallback
            );
        }
    }

    private boolean shouldPlayMove(int x, int y) {
        return squareIsFree(x, y) && gameNotWon();
    }

    private boolean gameNotWon() {
        return currentGameStatus.winner.equals(Player.noPlayer());
    }

    private boolean squareIsFree(int x, int y) {
        return currentGameStatus.board[x][y].equals(Player.noPlayer());
    }

    private Player getCurrentPlayer() {
        Player previousPlayer = currentGameStatus.lastPlayer;
        if (previousPlayer.equals(Player.player1())) {
            return Player.player2();
        } else {
            return Player.player1();
        }
    }

    @NonNull
    @Override
    public String getPlayerName(Player player) {
        if (player.equals(Player.player1())) {
            return "Player 1";
        } else if (player.equals(Player.player2())) {
            return "Player 2";
        } else {
            return "NO_PLAYER";
        }
    }

    @Override
    public int getGameId() {
        return currentGameStatus.gameId;
    }

    private void registerNetworkListener() {
        registerNetworkGameListenerUseCase.registerListener(
                gameNetworkListener,
                currentGameStatus.gameId
        );
    }

    private void onGameStatusReceived(GameStatus gameStatus) {
        currentGameStatus = gameStatus;
        updateView();
        view.displayDebugSnackbar(gameStatus.lastPlayedSquare.toString());
    }

    private void updateView() {
        updateBoardView();
        if (isGameWon()) {
            displayWinner();
        } else {
            updatePlayerName();
        }
    }

    private void displayWinner() {
        view.displayWinner(getPlayerName(currentGameStatus.winner));
    }

    private void updateBoardView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                updateSpecificSquareView(i, j);
            }
        }
    }

    private void updateSpecificSquareView(int x, int y) {
        Player playerInSquare = currentGameStatus.board[x][y];
        view.setSquareText(getSymbolForPlayer(playerInSquare), x, y);
    }

    private String getSymbolForPlayer(Player player) {
        if (player != null && !player.equals(Player.noPlayer())) {
            if (player.equals(Player.player1())) {
                return "x";
            } else if (player.equals(Player.player2())) {
                return "o";
            }
        }
        return "";
    }

    private boolean isGameWon() {
        return !currentGameStatus.winner.equals(Player.noPlayer());
    }

    private void updatePlayerName() {
        view.setCurrentPlayer(getCurrentPlayer(), currentGameStatus.lastPlayedSquare);
    }

    private String getCurrentPlayerName() {
        Player currentPlayer = getCurrentPlayer();
        return getPlayerName(currentPlayer);
    }

    private void onAddMoveError(GameError error) {
        //todo do something ?
        view.displayDebugSnackbar(error.reason);
    }
}

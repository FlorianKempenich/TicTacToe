package com.shockn745.presentation.game;

import android.support.annotation.NonNull;

import com.shockn745.application.driving.AddMoveUseCase;
import com.shockn745.application.driving.GameStatus;
import com.shockn745.application.driving.InitNewGameUseCase;
import com.shockn745.application.driving.Move;
import com.shockn745.application.driving.Player;
import com.shockn745.application.driving.implementation.GameError;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class GamePresenter implements GameContract.Presenter {
    private final GameContract.View view;

    private final InitNewGameUseCase initNewGameUseCase;
    private final AddMoveUseCase addMoveUseCase;

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
    private final InitNewGameUseCase.Callback initCallback = new InitNewGameUseCase.Callback() {
        @Override
        public void newGameReady(GameStatus newGame) {
            onGameStatusReceived(newGame);
        }
    };

    public GamePresenter(GameContract.View view, InitNewGameUseCase initNewGameUseCase, AddMoveUseCase addMoveUseCase) {
        this.view = view;
        this.initNewGameUseCase = initNewGameUseCase;
        this.addMoveUseCase = addMoveUseCase;
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
            addMoveUseCase.execute(new Move(x, y, currentPlayer), currentGameStatus.gameId, addMoveCallback);
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

    private void onGameStatusReceived(GameStatus gameStatus) {
        currentGameStatus = gameStatus;
        updateView();
    }

    private void updateView() {
        updateBoardView();
        updatePlayerName();
        checkForWinnerAndUpdateView();
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

    private void checkForWinnerAndUpdateView() {
        if (!currentGameStatus.winner.equals(Player.noPlayer())) {
            view.displayWinner(getPlayerName(currentGameStatus.winner));
        }
    }

    private void updatePlayerName() {
        view.setCurrentPlayerName(getCurrentPlayerName());
    }

    private String getCurrentPlayerName() {
        Player currentPlayer = getCurrentPlayer();
        return getPlayerName(currentPlayer);
    }

    @NonNull
    private String getPlayerName(Player player) {
        if (player.equals(Player.player1())) {
            return "Player 1";
        } else if (player.equals(Player.player2())) {
            return "Player 2";
        } else {
            return "NO_PLAYER";
        }
    }

    private void onAddMoveError(GameError error) {
        //todo do something ?
    }
}

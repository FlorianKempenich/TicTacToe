package com.shockn745.presentation;

import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.GameStatus;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.application.Move;
import com.shockn745.application.Player;
import com.shockn745.application.implementation.GameError;
import com.shockn745.utils.NullObjects;

/**
 * @author Kempenich Florian
 */
public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View view;

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

    public MainPresenter(MainContract.View view, InitNewGameUseCase initNewGameUseCase, AddMoveUseCase addMoveUseCase) {
        this.view = view;
        this.initNewGameUseCase = initNewGameUseCase;
        this.addMoveUseCase = addMoveUseCase;
    }

    @Override
    public void onCreate() {
        initNewGameUseCase.execute(initCallback);
    }

    @Override
    public void onSquareClicked(int x, int y) {
        if (squareIsFree(x, y)) {
            addMoveUseCase.execute(new Move(x, y, Player.player1()), currentGameStatus.gameId, addMoveCallback);
        }
    }

    private boolean squareIsFree(int x, int y) {
        return currentGameStatus.board[x][y].equals(Player.noPlayer());
    }

    private void onGameStatusReceived(GameStatus gameStatus) {
        currentGameStatus = gameStatus;
        updateView();
    }

    private void updateView() {
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

    private void onAddMoveError(GameError error) {

    }
}

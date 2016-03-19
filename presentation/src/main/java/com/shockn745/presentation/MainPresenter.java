package com.shockn745.presentation;

import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.GameStatus;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.application.Move;
import com.shockn745.application.Player;
import com.shockn745.application.implementation.GameError;

/**
 * @author Kempenich Florian
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
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
    private final InitNewGameUseCase initNewGameUseCase;
    private final AddMoveUseCase addMoveUseCase;
    private int gameId;
    private final InitNewGameUseCase.Callback initCallback = new InitNewGameUseCase.Callback() {
        @Override
        public void newGameReady(GameStatus newGame) {
            gameId = newGame.gameId;
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
        addMoveUseCase.execute(new Move(x, y, Player.player1()), gameId, addMoveCallback);
    }

    private void onGameStatusReceived(GameStatus gameStatus) {
        Player[][] board = gameStatus.board;
        updateView(board);
    }

    private void updateView(Player[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                view.setSquareText(getSymbolForPlayer(board[i][j]), i, j);
            }
        }
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

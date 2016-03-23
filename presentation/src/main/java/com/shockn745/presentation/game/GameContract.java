package com.shockn745.presentation.game;


import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.Player;

import java.util.Set;

/**
 * @author Kempenich Florian
 */
public interface GameContract {
    interface View {
        void setSquareText(String text, int x, int y);

        void setCurrentPlayer(Player player, BoardCoordinates lastSquarePlayed);

        void displayWinner(
                String winnerName,
                Set<BoardCoordinates> winningSquares,
                BoardCoordinates lastSquarePlayed);

        void displayDebugSnackbar(String message);
    }

    interface Presenter {
        void onCreate();

        void resetGame();

        void onSquareClicked(int x, int y);

        String getPlayerName(Player player);

        //todo remove : used for FakeMoveGenerator
        int getGameId();
    }
}

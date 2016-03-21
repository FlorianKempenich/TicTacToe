package com.shockn745.presentation.game;


import com.shockn745.application.driving.dto.Player;

/**
 * @author Kempenich Florian
 */
public interface GameContract {
    interface View {
        void setSquareText(String text, int x, int y);

        void setCurrentPlayer(Player player);

        void displayWinner(String winnerName);
    }

    interface Presenter {
        void onCreate();

        void resetGame();

        void onSquareClicked(int x, int y);

        String getPlayerName(Player player);
    }
}

package com.shockn745.presentation.game;

/**
 * @author Kempenich Florian
 */
public interface GameContract {
    interface View {
        void setSquareText(String text, int x, int y);

        void setCurrentPlayerName(String text);

        void displayWinner(String winnerName);
    }

    interface Presenter {
        void onCreate();

        void resetGame();

        void onSquareClicked(int x, int y);
    }
}

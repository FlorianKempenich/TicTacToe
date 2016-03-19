package com.shockn745.presentation;

/**
 * @author Kempenich Florian
 */
public interface MainContract {
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

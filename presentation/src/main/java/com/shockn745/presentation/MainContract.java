package com.shockn745.presentation;

/**
 * @author Kempenich Florian
 */
public interface MainContract {
    interface View {
        void setSquareText(String text, int x, int y);
    }

    interface Presenter {
        void onCreate();
        void onSquareClicked(int x, int y);
    }
}

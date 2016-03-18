package com.shockn745.application;

/**
 * @author Kempenich Florian
 */
public interface AddMoveUseCase {

    interface Callback {
        void onSuccess(GameStatus status);
    }

    void execute(Move move, int gameId, Callback callback);
}

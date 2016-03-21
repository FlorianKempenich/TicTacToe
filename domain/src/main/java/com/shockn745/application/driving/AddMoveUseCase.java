package com.shockn745.application.driving;

import com.shockn745.application.driving.implementation.GameError;

/**
 * @author Kempenich Florian
 */
public interface AddMoveUseCase {

    interface Callback {
        void onSuccess(GameStatus status);
        void onError(GameError error);
    }

    void execute(Move move, int gameId, Callback callback);
}

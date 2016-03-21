package com.shockn745.application.driving.presentation;

import com.shockn745.application.driving.dto.GameStatus;

/**
 * @author Kempenich Florian
 */
public interface InitNewGameUseCase{

    interface Callback {
        void newGameReady(GameStatus newGame);
    }

    void execute(Callback callback);

}

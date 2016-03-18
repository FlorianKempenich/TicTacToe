package com.shockn745.application;

/**
 * @author Kempenich Florian
 */
public interface InitNewGameUseCase{

    interface Callback {
        void newGameReady(GameStatus newGame);
    }

    void execute(Callback callback);

}

package com.shockn745.domain.factory;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Game;

/**
 * @author Kempenich Florian
 */
public interface GameFactory {
    Game newGame();

    Game makeGame(GameStatus status);
}

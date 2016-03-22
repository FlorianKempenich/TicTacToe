package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;

/**
 * @author Kempenich Florian
 */
public interface GameFactory {

    Game makeNewGame();

    Game makeGame(GameStatus status);

}

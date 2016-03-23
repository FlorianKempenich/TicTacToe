package com.shockn745.application.driven;

import com.shockn745.application.driving.dto.GameStatus;

/**
 * @author Kempenich Florian
 */
public interface GameStatusRepository {

    int saveGame(GameStatus game);

    boolean contains(int id);

    GameStatus getGame(int id);

}

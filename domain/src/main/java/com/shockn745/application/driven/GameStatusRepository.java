package com.shockn745.application.driven;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Game;

/**
 * @author Kempenich Florian
 */
public interface GameStatusRepository {

    int saveGame(GameStatus game);

    boolean contains(int id);

    GameStatus getGame(int id);

}

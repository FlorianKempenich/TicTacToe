package com.shockn745;

import com.shockn745.domain.Game;

/**
 * @author Kempenich Florian
 */
public interface GameRepository {

    int saveGame(Game game);

    boolean contains(int id);

    Game getGame(int id);
}

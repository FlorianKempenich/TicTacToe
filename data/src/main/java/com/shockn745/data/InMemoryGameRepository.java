package com.shockn745.data;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.domain.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kempenich Florian
 */
public class InMemoryGameRepository implements GameRepository {

    private Map<Integer, Game> map = new HashMap<>();
    private int counter = 0;

    /**
     * @return Id of the saved Game
     */
    @Override
    public int saveGame(Game game) {
        map.put(counter, game);
        int id = counter;
        counter++;
        return id;
    }

    @Override
    public boolean contains(int id) {
        return map.containsKey(id);
    }

    @Override
    public Game getGame(int id) {
        return map.get(id);
    }
}

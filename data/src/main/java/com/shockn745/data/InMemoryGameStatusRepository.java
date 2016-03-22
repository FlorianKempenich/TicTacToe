package com.shockn745.data;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kempenich Florian
 */
public class InMemoryGameStatusRepository implements GameStatusRepository {

    private Map<Integer, GameStatus> map = new HashMap<>();
    private int counter = 0;

    /**
     * Save a new GameStatus, or update an existing one.
     * To save a new game use the id provided by {@link GameStatus#NO_ID}
     *
     * @param gameStatus to save
     * @return id of the saved gameStatus
     */
    @Override
    public int saveGame(GameStatus gameStatus) {
        if (gameStatus.gameId == GameStatus.NO_ID) {
            return saveNewGame(gameStatus);
        } else {
            return updateExistingGame(gameStatus);
        }
    }

    private int updateExistingGame(GameStatus gameStatus) {
        checkIfGameExist(gameStatus);
        map.put(gameStatus.gameId, gameStatus);
        return gameStatus.gameId;
    }

    private void checkIfGameExist(GameStatus gameStatus) {
        if (!contains(gameStatus.gameId)) {
            throw new IllegalSaveException("Game not found! To save a new game use the NO_ID id");
        }
    }

    private int saveNewGame(GameStatus gameStatus) {
        int id = counter;
        GameStatus withId = gameStatus.updateWithId(id);
        map.put(counter, withId);
        counter++;
        return id;
    }

    @Override
    public boolean contains(int id) {
        return map.containsKey(id);
    }

    @Override
    public GameStatus getGame(int id) {
        return map.get(id);
    }


}

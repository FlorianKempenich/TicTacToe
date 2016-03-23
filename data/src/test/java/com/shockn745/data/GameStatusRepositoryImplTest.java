package com.shockn745.data;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.GameDataMapper;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class GameStatusRepositoryImplTest {

    GameStatusRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryGameStatusRepository();

    }

    @Test
    public void saveMultipleGames_allPresent() throws Exception {
        int id1 = repository.saveGame(makeNewGameStatusWithNoId());
        int id2 = repository.saveGame(makeNewGameStatusWithNoId());
        int id3 = repository.saveGame(makeNewGameStatusWithNoId());

        assertTrue(repository.contains(id1));
        assertTrue(repository.contains(id2));
        assertTrue(repository.contains(id3));
    }

    private static GameStatus makeNewGameStatusWithNoId() {
        GameFactory factory = new GameFactory();
        GameDataMapper gameDataMapper = new GameDataMapper(factory);
        Game game = factory.makeNewGame();
        return gameDataMapper.transform(game);
    }

    @Test
    public void getSavedGame_returnsCorrectGame() throws Exception {
        GameStatus gameStatus1 = makeNewGameStatusWithNoId();
        GameStatus gameStatus2 = makeNewGameStatusWithNoId();
        GameStatus gameStatus3 = makeNewGameStatusWithNoId();
        int id1 = repository.saveGame(gameStatus1);
        int id2 = repository.saveGame(gameStatus2);
        int id3 = repository.saveGame(gameStatus3);

        GameStatus result1 = repository.getGame(id1);
        GameStatus result2 = repository.getGame(id2);
        GameStatus result3 = repository.getGame(id3);

        GameStatus expected1 = gameStatus1.updateWithId(id1);
        GameStatus expected2 = gameStatus2.updateWithId(id2);
        GameStatus expected3 = gameStatus3.updateWithId(id3);

        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
        assertEquals(expected3, result3);
    }

    @Test
    public void saveExistingGame_doNotUpdateId() throws Exception {
        GameStatus gameStatus = makeNewGameStatusWithNoId();

        int originalId = repository.saveGame(gameStatus);
        GameStatus fromRepository = repository.getGame(originalId);

        int secondId = repository.saveGame(fromRepository);
        assertEquals(originalId, secondId);
    }

    @Test
    public void gameStatusWithId_notPresentInRepository_doNotSave() throws Exception {
        GameStatus emptyGameStatus = NullObjects.makeEmptyGameStatus(12);
        try {
            repository.saveGame(emptyGameStatus);
            fail();
        } catch (IllegalSaveException e) {
            assertEquals("Game not found! To save a new game use the NO_ID id", e.getMessage());
        }

    }
}

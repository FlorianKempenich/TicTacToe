package com.shockn745.data;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.domain.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kempenich Florian
 */
public class GameRepositoryImplTest {

    GameRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryGameRepository();

    }

    @Test
    public void saveMultipleGames_allPresent() throws Exception {
        int id1 = repository.saveGame(makeNewGame());
        int id2 = repository.saveGame(makeNewGame());
        int id3 = repository.saveGame(makeNewGame());

        assertTrue(repository.contains(id1));
        assertTrue(repository.contains(id2));
        assertTrue(repository.contains(id3));
    }

    private static Game makeNewGame() {
        GameFactory factory = new GameFactoryImpl();
        return factory.makeNewGame();
    }

    @Test
    public void getSavedGame_returnsCorrectGame() throws Exception {
        Game game1 = makeNewGame();
        Game game2 = makeNewGame();
        Game game3 = makeNewGame();
        int id1 = repository.saveGame(game1);
        int id2 = repository.saveGame(game2);
        int id3 = repository.saveGame(game3);

        Game result1 = repository.getGame(id1);
        Game result2 = repository.getGame(id2);
        Game result3 = repository.getGame(id3);

        assertEquals(game1, result1);
        assertEquals(game2, result2);
        assertEquals(game3, result3);
    }
}

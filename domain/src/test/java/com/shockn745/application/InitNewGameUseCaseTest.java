package com.shockn745.application;

import com.shockn745.GameRepository;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author Kempenich Florian
 */
public class InitNewGameUseCaseTest {

    /*
    NOTE :
    The idea is that the board is created in the activity and then passed to the game, etc . . . ==> The idea changed. will update comment ^_^

    All the use-cases must take the Game as a parameter.
    ++++ Use callback as response mechanism (See physical note for UX handling)

    Which means persistence is only there when the activity exist (in other words, there is
    actually no persistence)

    But that's an OK first step I think. I'll think about the persistence mechanism by
    implementing it in a new module. Either database/inMemory/File/Cloud. (in practice most
    likely file or inMemory)
     */

    InitNewGameUseCase initNewGameUseCase;

    @Mock
    GameRepository gameRepository;
    @Mock
    InitNewGameUseCase.Callback callback;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        initNewGameUseCase = new com.shockn745.application.implementation.InitNewGameUseCaseImpl(gameRepository);
    }

    @Test
    public void getNewGame_BoardIsEmpty() throws Exception {

        initNewGameUseCase.execute(callback);
        verify(callback).newGameReady(gameStatusArgumentCaptor.capture());

        GameStatus status = gameStatusArgumentCaptor.getValue();
        int id = status.gameId;

        assertEquals(NullObjects.makeEmptyGameStatus(id), status);
    }
}

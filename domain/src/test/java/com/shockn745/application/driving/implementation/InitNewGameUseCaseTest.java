package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.implementation.InitNewGameUseCaseImpl;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
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
    GameStatusRepository gameStatusRepository;
    @Mock
    InitNewGameUseCase.Callback callback;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        GameFactory gameFactory = new GameFactoryImpl();
        initNewGameUseCase = new InitNewGameUseCaseImpl(gameStatusRepository, gameFactory);
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

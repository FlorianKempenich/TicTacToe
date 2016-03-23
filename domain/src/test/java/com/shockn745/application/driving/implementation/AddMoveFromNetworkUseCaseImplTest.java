package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.testutil.GameStatusTestScenarios;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Kempenich Florian
 */
public class AddMoveFromNetworkUseCaseImplTest {

    private static final int EMPTY_GAME_ID = 1;
    private static final int MOVE_ON_00_GAME_ID = 2;

    AddMoveFromNetworkUseCase addMoveFromNetworkUseCase;

    @Mock
    NetworkListenerRepository listenersRepository;
    @Mock
    GameStatusRepository gameStatusRepository;
    @Mock
    NetworkListenerRepository.GameNetworkListener listener;
    @Mock
    AddMoveFromNetworkUseCase.Callback errorCallback;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusCaptor;
    @Captor
    ArgumentCaptor<GameError> gameErrorCaptor;

    GameStatusTestScenarios testScenarios;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        GameFactory gameFactory = new GameFactory();
        testScenarios = new GameStatusTestScenarios(gameFactory);
        GameMapper gameMapper = new GameMapper(gameFactory);
        addMoveFromNetworkUseCase = new AddMoveFromNetworkUseCaseImpl(
                gameStatusRepository,
                listenersRepository,
                gameMapper
        );


        // Mock listener repo behavior
        when(listenersRepository.getListeners(EMPTY_GAME_ID)).thenReturn(makeSetWithListener(
                listener));

        // Mock game repo behavior
        when(gameStatusRepository.contains(EMPTY_GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(EMPTY_GAME_ID)).thenReturn(testScenarios.makeEmptyGameStatus(
                EMPTY_GAME_ID));

        when(gameStatusRepository.contains(MOVE_ON_00_GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(MOVE_ON_00_GAME_ID)).thenReturn(testScenarios.makeGameStatusWithMoveOn00(
                MOVE_ON_00_GAME_ID));
    }

    private static Set<NetworkListenerRepository.GameNetworkListener> makeSetWithListener(
            NetworkListenerRepository.GameNetworkListener listener) {
        Set<NetworkListenerRepository.GameNetworkListener> set = new HashSet<>();
        set.add(listener);
        return set;
    }


    @Test
    public void addMoveToEmptyGame_Success_updateGameStateInRepository() throws Exception {
        Move move = new Move(new BoardCoordinates(0,0), Player.player1());
        addMoveFromNetworkUseCase.execute(move, EMPTY_GAME_ID, errorCallback);
        verify(gameStatusRepository).saveGame(any(GameStatus.class));
    }

    @Test
    public void addMove_listenerCalled() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(new BoardCoordinates(0,0), Player.player1()),
                EMPTY_GAME_ID,
                errorCallback
        );
        verify(listener).onNewMoveFromNetwork(any(GameStatus.class));

    }

    @Test
    public void addMove_MoveAdded() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(new BoardCoordinates(0,0), Player.player1()),
                EMPTY_GAME_ID,
                errorCallback
        );
        verify(listener).onNewMoveFromNetwork(gameStatusCaptor.capture());

        GameStatus status = gameStatusCaptor.getValue();

        assertEquals(testScenarios.makeGameStatusWithMoveOn00(EMPTY_GAME_ID), status);
    }

    @Test
    public void illegalMove_doNotCallListeners() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(new BoardCoordinates(0,0), Player.player1()),
                MOVE_ON_00_GAME_ID,
                errorCallback
        );
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void illegalMove_notifyErrorCallback() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(new BoardCoordinates(0,1), Player.player1()),
                MOVE_ON_00_GAME_ID,
                errorCallback
        );

        verify(errorCallback).onError(gameErrorCaptor.capture());
        GameError error = gameErrorCaptor.getValue();

        assertEquals("This player just played", error.reason);
    }
}
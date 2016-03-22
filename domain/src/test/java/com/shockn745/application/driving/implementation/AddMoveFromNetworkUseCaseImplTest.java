package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.domain.MoveModel;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

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
    GameRepository gameRepository;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        GameFactory gameFactory = new GameFactoryImpl();
        addMoveFromNetworkUseCase = new AddMoveFromNetworkUseCaseImpl(
                gameRepository,
                gameStatusRepository,
                listenersRepository,
                gameFactory
        );


        // Mock listener repo behavior
        when(listenersRepository.getListeners(EMPTY_GAME_ID)).thenReturn(makeSetWithListener(
                listener));

        // Mock game repo behavior
        when(gameStatusRepository.contains(EMPTY_GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(EMPTY_GAME_ID)).thenReturn(makeEmptyGameStatus());

        when(gameStatusRepository.contains(MOVE_ON_00_GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(MOVE_ON_00_GAME_ID)).thenReturn(makeGameStatusWithMoveOn00());
    }

    private static Set<NetworkListenerRepository.GameNetworkListener> makeSetWithListener(
            NetworkListenerRepository.GameNetworkListener listener) {
        Set<NetworkListenerRepository.GameNetworkListener> set = new HashSet<>();
        set.add(listener);
        return set;
    }

    private static GameStatus makeEmptyGameStatus() {
        GameFactory factory = new GameFactoryImpl();
        GameStatus emptyGameStatus = NullObjects.makeEmptyGameStatus(EMPTY_GAME_ID);
        return factory.makeGame(emptyGameStatus).makeStatus();
    }

    private static GameStatus makeGameStatusWithMoveOn00() throws Exception {
        GameFactory factory = new GameFactoryImpl();
        GameStatus emptyGameStatus = NullObjects.makeEmptyGameStatus(MOVE_ON_00_GAME_ID);
        Game game = factory.makeGame(emptyGameStatus);
        game.play(new MoveModel(0, 0, Player.player1()));
        return game.makeStatus();
    }

    @Test
    public void addMoveToEmptyGame_Success_updateGameStateInRepository() throws Exception {
        Move move = new Move(0, 0, Player.player1());
        addMoveFromNetworkUseCase.execute(move, EMPTY_GAME_ID, errorCallback);
        verify(gameStatusRepository).saveGame(any(GameStatus.class));
    }

    @Test
    public void addMove_listenerCalled() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(0, 0, Player.player1()),
                EMPTY_GAME_ID,
                errorCallback
        );
        verify(listener).onNewMoveFromNetwork(any(GameStatus.class));

    }

    @Test
    public void addMove_MoveAdded() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(0, 0, Player.player1()),
                EMPTY_GAME_ID,
                errorCallback
        );
        verify(listener).onNewMoveFromNetwork(gameStatusCaptor.capture());

        GameStatus status = gameStatusCaptor.getValue();

        GameStatus expectedStatus;
        Player[][] board = NullObjects.makeEmptyBoard();
        board[0][0] = Player.player1();
        Player lastPlayer = Player.player1();
        Player winner = Player.noPlayer();
        expectedStatus = new GameStatus(EMPTY_GAME_ID, board, lastPlayer, winner);

        assertEquals(expectedStatus, status);
    }

    @Test
    public void illegalMove_doNotCallListeners() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(0, 0, Player.player1()),
                MOVE_ON_00_GAME_ID,
                errorCallback
        );
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void illegalMove_notifyErrorCallback() throws Exception {
        addMoveFromNetworkUseCase.execute(
                new Move(0, 1, Player.player1()),
                MOVE_ON_00_GAME_ID,
                errorCallback
        );

        verify(errorCallback).onError(gameErrorCaptor.capture());
        GameError error = gameErrorCaptor.getValue();

        assertEquals("This player just played", error.reason);
    }
}
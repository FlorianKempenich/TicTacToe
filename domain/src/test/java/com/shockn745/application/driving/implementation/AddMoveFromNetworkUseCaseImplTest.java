package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameImpl;
import com.shockn745.utils.NullObjects;

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
import static org.mockito.Mockito.when;

/**
 * @author Kempenich Florian
 */
public class AddMoveFromNetworkUseCaseImplTest {

    private final int GAME_ID = 1;

    AddMoveFromNetworkUseCase addMoveFromNetworkUseCase;

    @Mock
    NetworkListenerRepository listenersRepository;
    @Mock
    GameRepository gameRepository;
    @Mock
    NetworkListenerRepository.GameNetworkListener listener;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        addMoveFromNetworkUseCase = new AddMoveFromNetworkUseCaseImpl(
                gameRepository,
                listenersRepository
        );


        // Mock listener repo behavior
        when(listenersRepository.getListeners(GAME_ID)).thenReturn(makeSetWithListener(listener));

        // Mock game repo behavior
        when(gameRepository.contains(GAME_ID)).thenReturn(true);
        when(gameRepository.getGame(GAME_ID)).thenReturn(makeEmptyGame());
    }

    private static Set<NetworkListenerRepository.GameNetworkListener> makeSetWithListener(
            NetworkListenerRepository.GameNetworkListener listener) {
        Set<NetworkListenerRepository.GameNetworkListener> set = new HashSet<>();
        set.add(listener);
        return set;
    }

    private static Game makeEmptyGame() {
        Board board = new BoardImpl();
        return new GameImpl(board);
    }

    @Test
    public void addMove_listenerCalled() throws Exception {
        addMoveFromNetworkUseCase.execute(new Move(0, 0, Player.player1()), GAME_ID);
        verify(listener).onNewMoveFromNetwork(any(GameStatus.class));

    }

    @Test
    public void addMove_MoveAdded() throws Exception {
        addMoveFromNetworkUseCase.execute(new Move(0, 0, Player.player1()), GAME_ID);
        verify(listener).onNewMoveFromNetwork(gameStatusCaptor.capture());

        GameStatus status = gameStatusCaptor.getValue();

        GameStatus expectedStatus;
        Player[][] board = NullObjects.makeEmptyBoard();
        board[0][0] = Player.player1();
        Player lastPlayer = Player.player1();
        Player winner = Player.noPlayer();
        expectedStatus = new GameStatus(GAME_ID, board, lastPlayer, winner);

        assertEquals(expectedStatus, status);
    }
}
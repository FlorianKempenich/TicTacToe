package com.shockn745.presentation.game;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.testutil.GameStatusTestScenarios;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Kempenich Florian
 */
public class GamePresenterTest_newBoard {

    private static final int GAME_ID = 1;

    GamePresenter presenter;

    @Mock
    GameContract.View view;
    @Mock
    InitNewGameUseCase initNewGameUseCase;
    @Mock
    RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase;
    @Mock
    AddMoveUseCase addMoveUseCase;
    @Captor
    ArgumentCaptor<InitNewGameUseCase.Callback> initArgumentCaptor;
    @Captor
    ArgumentCaptor<AddMoveUseCase.Callback> addMoveArgumentCaptor;
    @Captor
    ArgumentCaptor<String> textCaptor;
    @Captor
    ArgumentCaptor<NetworkListenerRepository.GameNetworkListener> networkListenerCaptor;
    GameStatusTestScenarios testScenarios;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GamePresenter(view,
                initNewGameUseCase,
                registerNetworkGameListenerUseCase,
                addMoveUseCase
        );
        testScenarios = new GameStatusTestScenarios(new GameFactoryImpl());
    }

    @Test
    public void onCreate_initNewGame() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(any(InitNewGameUseCase.Callback.class));
    }

    @Test
    public void onCreate_registerNetworkListener() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(initArgumentCaptor.capture());
        initArgumentCaptor.getValue().newGameReady(NullObjects.makeEmptyGameStatus(GAME_ID));
        verify(registerNetworkGameListenerUseCase).registerListener(
                any(NetworkListenerRepository.GameNetworkListener.class),
                anyInt()
        );
    }

    @Test
    public void newMoveFromNetwork_updateView() throws Exception {
        // Fake status received from network
        presenter.onCreate();
        verify(initNewGameUseCase).execute(initArgumentCaptor.capture());
        initArgumentCaptor.getValue().newGameReady(NullObjects.makeEmptyGameStatus(GAME_ID));
        verify(registerNetworkGameListenerUseCase).registerListener(
                networkListenerCaptor.capture(),
                anyInt()
        );

        GameStatus statusAfterFirstMove = testScenarios.makeGameStatusWithMoveOn00(GAME_ID);
        networkListenerCaptor.getValue().onNewMoveFromNetwork(statusAfterFirstMove);

        verify(view, times(2)).setSquareText(textCaptor.capture(), eq(0), eq(0));
        List<String> squareTexts = textCaptor.getAllValues();
        assertEquals(2, squareTexts.size()); //one for initialisation, one after network event
        assertEquals("x", squareTexts.get(1).toLowerCase());
    }

    @Test
    public void onCreate_displayNameFirstPlayer() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(initArgumentCaptor.capture());
        initArgumentCaptor.getValue().newGameReady(NullObjects.makeEmptyGameStatus(GAME_ID));
        verify(view).setCurrentPlayer(eq(Player.player1()));
    }

    @Test
    public void clickOnButton_AddNewMove_UpdateView() throws Exception {
        // Verify use case called
        presenter.onSquareClicked(0, 0);
        verify(addMoveUseCase).execute(
                eq(new Move(0, 0, Player.player1())),
                anyInt(),
                addMoveArgumentCaptor.capture()
        );

        // Simulate positive response
        GameStatus status = testScenarios.makeGameStatusWithMoveOn00(GAME_ID);
        addMoveArgumentCaptor.getValue().onSuccess(status);

        // Verify text updated on view
        verify(view).setSquareText(textCaptor.capture(), eq(0), eq(0));
        assertEquals("x", textCaptor.getValue().toLowerCase());
    }


}

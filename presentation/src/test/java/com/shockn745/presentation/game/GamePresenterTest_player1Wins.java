package com.shockn745.presentation.game;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.testutil.GameStatusTestScenarios;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test for the presenter after a move on the first 2 square by the player 1. Next move wins.
 *
 * @author Kempenich Florian
 */
public class GamePresenterTest_player1Wins {

    private static final int GAME_ID = 1;

    GameContract.Presenter presenter;
    GameStatus statusAfterFirstMoveOn00;

    @Mock
    GameContract.View view;
    @Mock
    InitNewGameUseCase initNewGameUseCase;
    @Mock
    RegisterNetworkGameListenerUseCase registerNetworkGameListenerUseCase;
    @Mock
    AddMoveUseCase addMoveUseCase;
    @Captor
    ArgumentCaptor<AddMoveUseCase.Callback> addMoveArgumentCaptor;
    GameStatusTestScenarios testScenarios;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GamePresenter(initNewGameUseCase,
                registerNetworkGameListenerUseCase, addMoveUseCase
        );
        ((GamePresenter) presenter).setView(view);
        testScenarios = new GameStatusTestScenarios(new GameFactoryImpl());
        statusAfterFirstMoveOn00 = testScenarios.makeGameStatusWithMoveOn00(GAME_ID);
    }


    @Test
    public void clickOnWinningSquare_WinnerAnnounced() throws Exception {
        presenter.onSquareClicked(2, 0); //winning move
        // Capture callback
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        // Simulate response
        addMoveArgumentCaptor.getValue()
                .onSuccess(testScenarios.makeGameStatusPlayer1WonFirstRow(GAME_ID));

        verify(view).displayWinner("Player 1");
    }

    @Test
    public void clickAfterGameWon_ignored() throws Exception {
        // Init presenter with winning state
        presenter.onSquareClicked(2, 0); //winning move
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        addMoveArgumentCaptor.getValue()
                .onSuccess(testScenarios.makeGameStatusPlayer1WonFirstRow(GAME_ID));


        presenter.onSquareClicked(1, 2);
        verifyNoMoreInteractions(addMoveUseCase);
    }

}

package com.shockn745.presentation.game;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.domain.factory.GameFactoryImpl;
import com.shockn745.testutil.GameStatusTestScenarios;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
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
        presenter = new GamePresenter(view, initNewGameUseCase,
                registerNetworkGameListenerUseCase, addMoveUseCase
        );
        testScenarios = new GameStatusTestScenarios(new GameFactoryImpl());
    }

    @Test
    public void clickOnWinningSquare_WinnerAnnounced() throws Exception {
        simulateLastClickAndPositiveResponse();
        verify(view).displayWinner(eq("Player 1"), any(Set.class), any(BoardCoordinates.class));
    }

    private void simulateLastClickAndPositiveResponse() {
        presenter.onSquareClicked(2, 0); //winning move
        // Capture callback
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        // Simulate response
        addMoveArgumentCaptor.getValue()
                .onSuccess(testScenarios.makeGameStatusPlayer1WonFirstRow(GAME_ID));
    }

    @Test
    public void clickAfterGameWon_ignored() throws Exception {
        simulateLastClickAndPositiveResponse();
        presenter.onSquareClicked(1, 2);
        verifyNoMoreInteractions(addMoveUseCase);
    }

    @Test
    public void clickOnWinningSquare_WinnerAnnouncedWithWinningSquaresAndLastSquare()
            throws Exception {
        simulateLastClickAndPositiveResponse();

        Set<BoardCoordinates> expectedWinningSquares = new HashSet<>(3);
        expectedWinningSquares.add(new BoardCoordinates(0, 0));
        expectedWinningSquares.add(new BoardCoordinates(1, 0));
        expectedWinningSquares.add(new BoardCoordinates(2, 0));

        BoardCoordinates expectedLastSquare = new BoardCoordinates(2, 0);

        verify(view).displayWinner(
                eq("Player 1"),
                eq(expectedWinningSquares),
                eq(expectedLastSquare)
        );
    }

}

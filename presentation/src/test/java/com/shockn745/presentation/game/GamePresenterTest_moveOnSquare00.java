package com.shockn745.presentation.game;

import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.presentation.InitNewGameUseCase;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;
import com.shockn745.presentation.testutils.GameStatusUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test for the main presenter after a move on the first square
 *
 * @author Kempenich Florian
 */
public class GamePresenterTest_moveOnSquare00 {

    private static final int GAME_ID = 1;

    GameContract.Presenter presenter;

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GamePresenter(view, initNewGameUseCase,
                registerNetworkGameListenerUseCase, addMoveUseCase);

        // Play first move
        presenter.onSquareClicked(0, 0);
        verify(addMoveUseCase).execute(eq(new Move(0, 0, Player.player1())), anyInt(), addMoveArgumentCaptor.capture());
        GameStatus status = GameStatusUtil.makeAfterFirstMoveOn00(GAME_ID);
        addMoveArgumentCaptor.getValue().onSuccess(status);
    }

    @Test
    public void clickOnButton_AddSecondMoveWithSecondPlayer() throws Exception {
        presenter.onSquareClicked(1, 0);
        verify(addMoveUseCase).execute(eq(new Move(1, 0, Player.player2())), anyInt(), any(AddMoveUseCase.Callback.class));
    }

    @Test
    public void clickOnOccupiedSquare_ignoreClick() throws Exception {
        presenter.onSquareClicked(0, 0);
        verifyNoMoreInteractions(addMoveUseCase);
    }

    @Test
    public void resetGame_initANewGame() throws Exception {
        presenter.resetGame();
        verify(initNewGameUseCase).execute(any(InitNewGameUseCase.Callback.class));

    }
}

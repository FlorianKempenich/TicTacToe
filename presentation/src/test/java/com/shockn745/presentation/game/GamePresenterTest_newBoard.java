package com.shockn745.presentation.game;

import com.shockn745.application.driving.AddMoveUseCase;
import com.shockn745.application.driving.GameStatus;
import com.shockn745.application.driving.InitNewGameUseCase;
import com.shockn745.application.driving.Move;
import com.shockn745.application.driving.Player;
import com.shockn745.presentation.testutils.GameStatusUtil;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
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
    AddMoveUseCase addMoveUseCase;
    @Captor
    ArgumentCaptor<InitNewGameUseCase.Callback> initArgumentCaptor;
    @Captor
    ArgumentCaptor<AddMoveUseCase.Callback> addMoveArgumentCaptor;
    @Captor
    ArgumentCaptor<String> textCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GamePresenter(view, initNewGameUseCase, addMoveUseCase);
    }

    @Test
    public void onCreate_initNewGame() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(any(InitNewGameUseCase.Callback.class));
    }

    @Test
    public void onCreate_displayNameFirstPlayer() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(initArgumentCaptor.capture());
        initArgumentCaptor.getValue().newGameReady(NullObjects.makeEmptyGameStatus(GAME_ID));
        verify(view).setCurrentPlayerName(eq("Player 1"));
    }

    @Test
    public void clickOnButton_AddNewMove_UpdateView() throws Exception {
        // Verify use case called
        presenter.onSquareClicked(0, 0);
        verify(addMoveUseCase).execute(eq(new Move(0, 0, Player.player1())), anyInt(), addMoveArgumentCaptor.capture());

        // Simulate positive response
        GameStatus status = GameStatusUtil.makeAfterFirstMoveOn00(GAME_ID);
        addMoveArgumentCaptor.getValue().onSuccess(status);

        // Verify text updated on view
        verify(view).setSquareText(textCaptor.capture(), eq(0), eq(0));
        assertEquals("x", textCaptor.getValue().toLowerCase());
    }

}

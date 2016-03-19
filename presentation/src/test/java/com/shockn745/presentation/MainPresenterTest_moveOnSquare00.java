package com.shockn745.presentation;

import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.GameStatus;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.application.Move;
import com.shockn745.application.Player;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Kempenich Florian
 */
public class MainPresenterTest_moveOnSquare00 {

    private static final int GAME_ID = 1;

    MainPresenter presenter;
    GameStatus statusAfterFirstMoveOn00;

    @Mock
    MainContract.View view;
    @Mock
    InitNewGameUseCase initNewGameUseCase;
    @Mock
    AddMoveUseCase addMoveUseCase;
    @Captor
    ArgumentCaptor<AddMoveUseCase.Callback> addMoveArgumentCaptor;
    @Captor
    ArgumentCaptor<String> textCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(view, initNewGameUseCase, addMoveUseCase);
        statusAfterFirstMoveOn00 = GameStatusUtil.makeAfterFirstMoveOn00(GAME_ID);

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
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        GameStatus status = GameStatusUtil.makeAfterFirstMoveOn00(GAME_ID);
        addMoveArgumentCaptor.getValue().onSuccess(status);

        presenter.onSquareClicked(0, 0);
        verifyNoMoreInteractions(addMoveUseCase);
    }

}

package com.shockn745.presentation;

import com.shockn745.application.AddMoveUseCase;
import com.shockn745.application.GameStatus;
import com.shockn745.application.InitNewGameUseCase;
import com.shockn745.application.Move;
import com.shockn745.application.Player;
import com.shockn745.testutil.UseCaseTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author Kempenich Florian
 */
public class MainPresenterTest {

    private static final int GAME_ID = 1;

    MainPresenter presenter;

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
    }

    @Test
    public void onCreate_initNewGame() throws Exception {
        presenter.onCreate();
        verify(initNewGameUseCase).execute(any(InitNewGameUseCase.Callback.class));
    }

    @Test
    public void clickOnButton_AddNewMove_UpdateView() throws Exception {
        Player[][] boardWithMoveOn00Square = UseCaseTestUtil.makeEmtpyBoard();
        boardWithMoveOn00Square[0][0] = Player.player1();
        GameStatus status = new GameStatus(GAME_ID, boardWithMoveOn00Square, Player.player1(), Player.noPlayer());

        // Verify use case called
        presenter.onSquareClicked(0, 0);
        verify(addMoveUseCase).execute(eq(new Move(0, 0, Player.player1())), anyInt(), addMoveArgumentCaptor.capture());

        // Simulate positive response
        addMoveArgumentCaptor.getValue().onSuccess(status);

        // Verify text updated on view
        verify(view).setSquareText(textCaptor.capture(), eq(0), eq(0));
        assertEquals("x", textCaptor.getValue().toLowerCase());
    }
}

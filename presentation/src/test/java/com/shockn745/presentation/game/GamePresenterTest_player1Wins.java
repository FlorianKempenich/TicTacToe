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

    GamePresenter presenter;
    GameStatus statusAfterFirstMoveOn00;

    @Mock
    GameContract.View view;
    @Mock
    InitNewGameUseCase initNewGameUseCase;
    @Mock
    AddMoveUseCase addMoveUseCase;
    @Captor
    ArgumentCaptor<AddMoveUseCase.Callback> addMoveArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GamePresenter(view, initNewGameUseCase, addMoveUseCase);
        statusAfterFirstMoveOn00 = GameStatusUtil.makeAfterFirstMoveOn00(GAME_ID);
    }


    @Test
    public void clickOnWinningSquare_WinnerAnnounced() throws Exception {
        presenter.onSquareClicked(2, 0); //winning move
        // Capture callback
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        // Simulate response
        addMoveArgumentCaptor.getValue().onSuccess(makeStatusPlayer1Won(GAME_ID));

        verify(view).displayWinner("Player 1");
    }

    private static GameStatus makeStatusPlayer1Won(int gameId) {
        Player[][] board = NullObjects.makeEmptyBoard();
        // Play winning moves from player one
        board[0][0] = Player.player1();
        board[1][0] = Player.player1();
        board[2][0] = Player.player1();

        // Make game valid
        board[1][1] = Player.player2();
        board[2][2] = Player.player2();

        Player lastPlayer = Player.player1();
        Player winner = Player.player1();

        return new GameStatus(gameId, board, lastPlayer, winner);
    }

    @Test
    public void clickAfterGameWon_ignored() throws Exception {
        // Init presenter with winning state
        presenter.onSquareClicked(2, 0); //winning move
        verify(addMoveUseCase).execute(any(Move.class), anyInt(), addMoveArgumentCaptor.capture());
        addMoveArgumentCaptor.getValue().onSuccess(makeStatusPlayer1Won(GAME_ID));


        presenter.onSquareClicked(1, 2);
        verifyNoMoreInteractions(addMoveUseCase);
    }

}

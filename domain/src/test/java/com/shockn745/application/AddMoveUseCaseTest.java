package com.shockn745.application;

import com.shockn745.GameRepository;
import com.shockn745.application.implementation.AddMoveUseCaseImpl;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameImpl;
import com.shockn745.domain.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseTest {

    private static final int GAME_ID = 1;
    AddMoveUseCase addMoveUseCase;
    Game emptyGame;
    @Mock
    GameRepository gameRepository;
    @Mock
    AddMoveUseCase.Callback callback;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        addMoveUseCase = new AddMoveUseCaseImpl(gameRepository);
        initEmptyGame();
        addEmptyGameToRepository();
    }

    private void initEmptyGame() {
        Board board = new BoardImpl();
        emptyGame = new GameImpl(board);
    }

    private void addEmptyGameToRepository() {
        when(gameRepository.contains(GAME_ID)).thenReturn(true);
        when(gameRepository.getGame(GAME_ID)).thenReturn(emptyGame);
    }

    @Test
    public void addMoveToEmptyGame_Success_verifyMoveAddedRightPosition() throws Exception {
        Move move = new Move(0, 0, Player.player1());

        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus resultStatus = gameStatusArgumentCaptor.getValue();

        // Make gameStatus with move on first square
        Player[][] expectedBoard = UseCaseTestUtil.makeEmtpyBoard();
        expectedBoard[0][0] = Player.player1();
        GameStatus expectedStatus = new GameStatus(GAME_ID, expectedBoard, Player.player1(), Player.noPlayer());

        assertEquals(expectedStatus, resultStatus);
    }


}

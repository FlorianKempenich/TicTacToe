package com.shockn745.application;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driving.GameStatus;
import com.shockn745.application.driving.Move;
import com.shockn745.application.driving.Player;
import com.shockn745.application.driving.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.driving.implementation.GameError;
import com.shockn745.domain.Board;
import com.shockn745.domain.BoardImpl;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameImpl;
import com.shockn745.domain.MoveModel;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseTest {

    private static final int GAME_ID = 1;
    com.shockn745.application.driving.AddMoveUseCase addMoveUseCase;
    Game emptyGame;
    @Mock
    GameRepository gameRepository;
    @Mock
    com.shockn745.application.driving.AddMoveUseCase.Callback callback;
    @Captor
    ArgumentCaptor<com.shockn745.application.driving.GameStatus> gameStatusArgumentCaptor;
    @Captor
    ArgumentCaptor<GameError> gameErrorArgumentCaptor;

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
        com.shockn745.application.driving.Move move = new com.shockn745.application.driving.Move(0, 0, com.shockn745.application.driving.Player.player1());

        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        com.shockn745.application.driving.GameStatus resultStatus = gameStatusArgumentCaptor.getValue();

        // Make gameStatus with move on first square
        com.shockn745.application.driving.Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = com.shockn745.application.driving.Player.player1();
        com.shockn745.application.driving.GameStatus expectedStatus = new com.shockn745.application.driving.GameStatus(GAME_ID, expectedBoard, com.shockn745.application.driving.Player.player1(), com.shockn745.application.driving.Player.noPlayer());

        assertEquals(expectedStatus, resultStatus);
    }

    @Test
    public void add2MovesToTheSameSquare_Error_checkErrorMessage() throws Exception {
        com.shockn745.application.driving.Move move1 = new com.shockn745.application.driving.Move(0,0, com.shockn745.application.driving.Player.player1());
        com.shockn745.application.driving.Move move2 = new com.shockn745.application.driving.Move(0,0, com.shockn745.application.driving.Player.player2());

        // Add first move to emptyGame
        emptyGame.play(new MoveModel(move1));

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This square has already been played", gameError.reason);
    }

    @Test
    public void add2MovesSamePlayer_Error_checkErrorMessage() throws Exception {
        com.shockn745.application.driving.Move move1 = new com.shockn745.application.driving.Move(0,0, com.shockn745.application.driving.Player.player1());
        com.shockn745.application.driving.Move move2 = new com.shockn745.application.driving.Move(1,1, com.shockn745.application.driving.Player.player1());

        // Add first move to emptyGame
        emptyGame.play(new MoveModel(move1));

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This player just played", gameError.reason);
    }

    @Test
    public void addLastMove_GameFinished_correctWinner() throws Exception {
        // Setup game -- Play all the moves except last one -- Player 1 wins on first line
        emptyGame.play(new MoveModel(0,0, com.shockn745.application.driving.Player.player1()));
        emptyGame.play(new MoveModel(1,1, com.shockn745.application.driving.Player.player2()));
        emptyGame.play(new MoveModel(1,0, com.shockn745.application.driving.Player.player1()));
        emptyGame.play(new MoveModel(2,2, com.shockn745.application.driving.Player.player2()));
        // Last play : 2-0 Player 1 : Left for use-case

        // Make expected game status
        com.shockn745.application.driving.Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = com.shockn745.application.driving.Player.player1();
        expectedBoard[1][1] = com.shockn745.application.driving.Player.player2();
        expectedBoard[1][0] = com.shockn745.application.driving.Player.player1();
        expectedBoard[2][2] = com.shockn745.application.driving.Player.player2();
        expectedBoard[2][0] = com.shockn745.application.driving.Player.player1();

        com.shockn745.application.driving.Player lastPlayer = com.shockn745.application.driving.Player.player1();
        com.shockn745.application.driving.Player winner = com.shockn745.application.driving.Player.player1();

        com.shockn745.application.driving.GameStatus expectedStatus = new com.shockn745.application.driving.GameStatus(GAME_ID, expectedBoard, lastPlayer, winner);

        addMoveUseCase.execute(new com.shockn745.application.driving.Move(2, 0, com.shockn745.application.driving.Player.player1()), GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus result = gameStatusArgumentCaptor.getValue();

        assertEquals(expectedStatus, result);
    }

    @Test
    public void addMove_GameNotFoundInRepository_Error() throws Exception {
        int invalidId = -1;
        when(gameRepository.contains(eq(invalidId))).thenReturn(false);
        when(gameRepository.getGame(eq(invalidId))).thenReturn(null);

        addMoveUseCase.execute(new Move(0,0, Player.player1()), invalidId, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        assertEquals("Game not found : ID=" + invalidId, gameErrorArgumentCaptor.getValue().reason);
    }

    //TODO test case when game in repository not available
}

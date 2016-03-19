package com.shockn745.application;

import com.shockn745.GameRepository;
import com.shockn745.application.implementation.AddMoveUseCaseImpl;
import com.shockn745.application.implementation.GameError;
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
        Move move = new Move(0, 0, Player.player1());

        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus resultStatus = gameStatusArgumentCaptor.getValue();

        // Make gameStatus with move on first square
        Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = Player.player1();
        GameStatus expectedStatus = new GameStatus(GAME_ID, expectedBoard, Player.player1(), Player.noPlayer());

        assertEquals(expectedStatus, resultStatus);
    }

    @Test
    public void add2MovesToTheSameSquare_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(0,0, Player.player1());
        Move move2 = new Move(0,0, Player.player2());

        // Add first move to emptyGame
        emptyGame.play(new MoveModel(move1));

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This square has already been played", gameError.reason);
    }

    @Test
    public void add2MovesSamePlayer_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(0,0, Player.player1());
        Move move2 = new Move(1,1, Player.player1());

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
        emptyGame.play(new MoveModel(0,0, Player.player1()));
        emptyGame.play(new MoveModel(1,1, Player.player2()));
        emptyGame.play(new MoveModel(1,0, Player.player1()));
        emptyGame.play(new MoveModel(2,2, Player.player2()));
        // Last play : 2-0 Player 1 : Left for use-case

        // Make expected game status
        Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = Player.player1();
        expectedBoard[1][1] = Player.player2();
        expectedBoard[1][0] = Player.player1();
        expectedBoard[2][2] = Player.player2();
        expectedBoard[2][0] = Player.player1();

        Player lastPlayer = Player.player1();
        Player winner = Player.player1();

        GameStatus expectedStatus = new GameStatus(GAME_ID, expectedBoard, lastPlayer, winner);

        addMoveUseCase.execute(new Move(2, 0, Player.player1()), GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus result = gameStatusArgumentCaptor.getValue();

        assertEquals(expectedStatus, result);
    }

    //TODO test case when game in repository not available
}

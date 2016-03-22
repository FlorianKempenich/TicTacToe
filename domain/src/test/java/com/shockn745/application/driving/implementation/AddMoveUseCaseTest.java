package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.GameFactoryImpl;
import com.shockn745.domain.MoveModel;
import com.shockn745.utils.NullObjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Kempenich Florian
 */
public class AddMoveUseCaseTest {

    private static final int GAME_ID = 1;
    AddMoveUseCase addMoveUseCase;
    Game game;
    @Mock
    GameStatusRepository gameStatusRepository;
    @Mock
    AddMoveUseCase.Callback callback;
    @Captor
    ArgumentCaptor<GameStatus> gameStatusArgumentCaptor;
    @Captor
    ArgumentCaptor<GameError> gameErrorArgumentCaptor;
    GameFactory gameFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        gameFactory = new GameFactoryImpl();
        addMoveUseCase = new AddMoveUseCaseImpl(gameStatusRepository, gameFactory);
        GameStatus emptyGameStatus = NullObjects.makeEmptyGameStatus(GAME_ID);
        game = gameFactory.makeGame(emptyGameStatus);
    }

    @Test
    public void addMoveToEmptyGame_Success_updateGameStateInRepository() throws Exception {
        updateGameInRepository();
        Move move = new Move(0, 0, Player.player1());
        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(gameStatusRepository).saveGame(any(GameStatus.class));
    }

    private void updateGameInRepository() {
        when(gameStatusRepository.contains(GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(GAME_ID)).thenReturn(game.makeStatus());
    }

    @Test
    public void addMoveToEmptyGame_Success_verifyMoveAddedRightPosition() throws Exception {
        updateGameInRepository();
        Move move = new Move(0, 0, Player.player1());
        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus resultStatus = gameStatusArgumentCaptor.getValue();

        // Make gameStatus with move on first square
        Player[][] expectedBoard = NullObjects.makeEmptyBoard();
        expectedBoard[0][0] = Player.player1();
        GameStatus expectedStatus =
                new GameStatus(GAME_ID, expectedBoard, Player.player1(), Player.noPlayer());

        assertEquals(expectedStatus, resultStatus);
    }

    @Test
    public void add2MovesToTheSameSquare_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(0, 0, Player.player1());
        Move move2 = new Move(0, 0, Player.player2());

        // Add first move to emptyGame
        game.play(new MoveModel(move1));
        updateGameInRepository();

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This square has already been played", gameError.reason);
    }

    @Test
    public void add2MovesSamePlayer_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(0, 0, Player.player1());
        Move move2 = new Move(1, 1, Player.player1());

        // Add first move to emptyGame
        game.play(new MoveModel(move1));
        updateGameInRepository();

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This player just played", gameError.reason);
    }

    @Test
    public void addLastMove_GameFinished_correctWinner() throws Exception {
        // Setup game -- Play all the moves except last one -- Player 1 wins on first line
        game.play(new MoveModel(0, 0, Player.player1()));
        game.play(new MoveModel(1, 1, Player.player2()));
        game.play(new MoveModel(1, 0, Player.player1()));
        game.play(new MoveModel(2, 2, Player.player2()));
        updateGameInRepository();
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

    @Test
    public void addMove_GameNotFoundInRepository_Error() throws Exception {
        int invalidId = -1;
        when(gameStatusRepository.contains(eq(invalidId))).thenReturn(false);
        when(gameStatusRepository.getGame(eq(invalidId))).thenReturn(null);

        addMoveUseCase.execute(new Move(0, 0, Player.player1()), invalidId, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        assertEquals("Game not found : ID=" + invalidId, gameErrorArgumentCaptor.getValue().reason);
    }
}

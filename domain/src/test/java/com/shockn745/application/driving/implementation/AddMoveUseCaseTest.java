package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.application.driving.presentation.AddMoveUseCase;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.MapperFactory;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.domain.datamapper.MoveMapper;
import com.shockn745.testutil.GameStatusTestScenarios;
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
    GameStatusTestScenarios testScenarios;
    GameMapper gameMapper;
    MoveMapper moveMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        GameFactory gameFactory = new GameFactory();
        MapperFactory mapperFactory = new MapperFactory();
        gameMapper = mapperFactory.gameMapper();
        moveMapper = new MoveMapper(new CoordinatesMapper());
        testScenarios = new GameStatusTestScenarios(gameFactory);
        addMoveUseCase = new AddMoveUseCaseImpl(gameStatusRepository, gameMapper);
        GameStatus emptyGameStatus = NullObjects.makeEmptyGameStatus(GAME_ID);
        game = gameFactory.makeGame(emptyGameStatus);
    }

    @Test
    public void addMoveToEmptyGame_Success_updateGameStateInRepository() throws Exception {
        updateGameInRepository();
        Move move = new Move(new BoardCoordinates(0,0), Player.player1());
        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(gameStatusRepository).saveGame(any(GameStatus.class));
    }

    private void updateGameInRepository() {
        when(gameStatusRepository.contains(GAME_ID)).thenReturn(true);
        when(gameStatusRepository.getGame(GAME_ID)).thenReturn(gameMapper.transform(game));
    }

    @Test
    public void addMoveToEmptyGame_Success_verifyMoveAddedRightPosition() throws Exception {
        updateGameInRepository();
        Move move = new Move(new BoardCoordinates(0,0), Player.player1());
        addMoveUseCase.execute(move, GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus resultStatus = gameStatusArgumentCaptor.getValue();

        assertEquals(testScenarios.makeGameStatusWithMoveOn00(GAME_ID), resultStatus);
    }

    @Test
    public void add2MovesToTheSameSquare_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(new BoardCoordinates(0,0), Player.player1());
        Move move2 = new Move(new BoardCoordinates(0,0), Player.player2());

        // Add first move to emptyGame
        game.play(moveMapper.transform(move1));
        updateGameInRepository();

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This square has already been played", gameError.reason);
    }

    @Test
    public void add2MovesSamePlayer_Error_checkErrorMessage() throws Exception {
        Move move1 = new Move(new BoardCoordinates(0,0), Player.player1());
        Move move2 = new Move(new BoardCoordinates(1,1), Player.player1());

        // Add first move to emptyGame
        game.play(moveMapper.transform(move1));
        updateGameInRepository();

        addMoveUseCase.execute(move2, GAME_ID, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        GameError gameError = gameErrorArgumentCaptor.getValue();

        assertEquals("This player just played", gameError.reason);
    }

    @Test
    public void addLastMove_GameFinished_correctWinner() throws Exception {
        // Setup game -- Play all the moves except last one -- Player 1 wins on first line
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(0, 0), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 1), Player.player2()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(1, 0), Player.player1()));
        game.play(new MoveModel(BoardCoordinatesModel.fromCoordinates(2, 2), Player.player2()));
        updateGameInRepository();
        // Last play : 2-0 Player 1 : Left for use-case

        GameStatus expectedStatus = testScenarios.makeGameStatusPlayer1WonFirstRow(GAME_ID);

        addMoveUseCase.execute(new Move(new BoardCoordinates(2,0), Player.player1()), GAME_ID, callback);
        verify(callback).onSuccess(gameStatusArgumentCaptor.capture());

        GameStatus result = gameStatusArgumentCaptor.getValue();

        assertEquals(expectedStatus, result);
    }

    @Test
    public void addMove_GameNotFoundInRepository_Error() throws Exception {
        int invalidId = -1;
        when(gameStatusRepository.contains(eq(invalidId))).thenReturn(false);
        when(gameStatusRepository.getGame(eq(invalidId))).thenReturn(null);

        addMoveUseCase.execute(new Move(new BoardCoordinates(0,0), Player.player1()), invalidId, callback);
        verify(callback).onError(gameErrorArgumentCaptor.capture());

        assertEquals(
                "Game not found : ID=" + invalidId,
                gameErrorArgumentCaptor.getValue().reason
        );
    }
}

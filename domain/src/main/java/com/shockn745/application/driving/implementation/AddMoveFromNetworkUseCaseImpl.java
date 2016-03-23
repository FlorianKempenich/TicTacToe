package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.Game;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.MoveModel;
import com.shockn745.domain.datamapper.GameDataMapper;
import com.shockn745.domain.exceptions.IllegalMoveException;

import java.util.Set;

/**
 * @author Kempenich Florian
 */
public class AddMoveFromNetworkUseCaseImpl implements AddMoveFromNetworkUseCase {

    private final GameStatusRepository gameStatusRepository;
    private final NetworkListenerRepository networkListenerRepository;
    private final GameDataMapper gameDataMapper;

    public AddMoveFromNetworkUseCaseImpl(
            GameStatusRepository gameStatusRepository,
            NetworkListenerRepository networkListenerRepository,
            GameDataMapper gameDataMapper) {
        this.gameStatusRepository = gameStatusRepository;
        this.networkListenerRepository = networkListenerRepository;
        this.gameDataMapper = gameDataMapper;
    }

    @Override
    public void execute(Move move, int gameId, Callback errorCallback) {
        if (shouldExecute(gameId)) {
            playMoveAndNotifyListeners(move, gameId, errorCallback);
        }
    }

    private boolean shouldExecute(int gameId) {
        return getListeners(gameId) != null && gameStatusRepository.contains(gameId);
    }

    private Set<NetworkListenerRepository.GameNetworkListener> getListeners(int gameId) {
        return networkListenerRepository.getListeners(gameId);
    }

    private void playMoveAndNotifyListeners(Move move, int gameId, Callback errorCallback) {
        GameStatus gameStatus = gameStatusRepository.getGame(gameId);
        Game game = gameDataMapper.transform(gameStatus);

        try {
            GameStatus status = playMoveAndGetStatus(move, game);
            gameStatusRepository.saveGame(status);
            notifyListeners(gameId, status);
        } catch (IllegalMoveException e) {
            GameError error = new GameError(e.getMessage());
            errorCallback.onError(error);
        }

    }

    private GameStatus playMoveAndGetStatus(Move move, Game game)
            throws IllegalMoveException {
        game.play(new MoveModel(move));
        game.checkIfFinishedAndUpdateWinner();
        return gameDataMapper.transform(game);
    }

    private void notifyListeners(int gameId, GameStatus status) {
        for (NetworkListenerRepository.GameNetworkListener listener : getListeners(gameId)) {
            listener.onNewMoveFromNetwork(status);
        }
    }


}

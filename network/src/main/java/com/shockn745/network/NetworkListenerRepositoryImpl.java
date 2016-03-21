package com.shockn745.network;

import com.shockn745.application.driven.NetworkListenerRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kempenich Florian
 */
public class NetworkListenerRepositoryImpl implements NetworkListenerRepository {

    Map<Integer, Set<GameNetworkListener>> gameListenersForGameId = new HashMap<>();

    @Override
    public void registerListener(GameNetworkListener listener, int gameId) {
        Set<GameNetworkListener> listenersForSpecificGame = getExistingListOrCreateNewOne(gameId);
        listenersForSpecificGame.add(listener);
        gameListenersForGameId.put(gameId, listenersForSpecificGame);
    }

    private Set<GameNetworkListener> getExistingListOrCreateNewOne(int gameId) {
        Set<GameNetworkListener> listenersForSpecificGame;
        listenersForSpecificGame = getListeners(gameId);
        if (listenersForSpecificGame == null) {
            listenersForSpecificGame = new HashSet<>();
        }
        return listenersForSpecificGame;
    }

    @Override
    public Set<GameNetworkListener> getListeners(int gameId) {
        return gameListenersForGameId.get(gameId);
    }

    @Override
    public void removeListener(GameNetworkListener listener, int gameId) {
        Set<GameNetworkListener> listeners = getListeners(gameId);
        if (listeners != null) {
            listeners.remove(listener);
            gameListenersForGameId.put(gameId, listeners);
        }
    }
}

package com.shockn745.application.driven;

import com.shockn745.application.driving.dto.GameStatus;

import java.util.List;
import java.util.Set;

/**
 * @author Kempenich Florian
 */
public interface NetworkListenerRepository {

    void registerListener(GameNetworkListener listener, int gameId);

    Set<GameNetworkListener> getListeners(int gameId);

    void removeListener(GameNetworkListener listener, int gameId);

    interface GameNetworkListener {
        void onNewMoveFromNetwork(GameStatus status);
    }
}

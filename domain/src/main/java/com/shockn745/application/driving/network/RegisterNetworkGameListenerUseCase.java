package com.shockn745.application.driving.network;

import com.shockn745.application.driving.dto.GameStatus;

/**
 * @author Kempenich Florian
 */
public interface RegisterNetworkGameListenerUseCase {

    interface GameNetworkListener {
        void onNewMoveFromNetwork(GameStatus status);
    }

    void registerListener(GameNetworkListener listener, int gameId);
}

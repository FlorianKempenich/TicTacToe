package com.shockn745.application.driving.network;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.GameStatus;

/**
 * @author Kempenich Florian
 */
public interface RegisterNetworkGameListenerUseCase {

    void registerListener(NetworkListenerRepository.GameNetworkListener listener, int gameId);
}

package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;

/**
 * @author Kempenich Florian
 */
public class RegisterNetworkGameListenerUseCaseImpl implements RegisterNetworkGameListenerUseCase{

    private final NetworkListenerRepository networkListenerRepository;

    public RegisterNetworkGameListenerUseCaseImpl(NetworkListenerRepository networkListenerRepository) {
        this.networkListenerRepository = networkListenerRepository;
    }

    @Override
    public void registerListener(NetworkListenerRepository.GameNetworkListener listener, int gameId) {
        networkListenerRepository.registerListener(listener, gameId);
    }
}

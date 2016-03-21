package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.network.RegisterNetworkGameListenerUseCase;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Kempenich Florian
 */
public class RegisterNetworkGameListenerUseCaseImpl implements RegisterNetworkGameListenerUseCase{

    private final NetworkListenerRepository networkListenerRepository;

    public RegisterNetworkGameListenerUseCaseImpl(NetworkListenerRepository networkListenerRepository) {
        this.networkListenerRepository = networkListenerRepository;
    }

    @Override
    public void registerListener(GameNetworkListener listener, int gameId) {
        throw new NotImplementedException();
    }
}

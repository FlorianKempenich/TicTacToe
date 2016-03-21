package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.application.driving.dto.Move;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Kempenich Florian
 */
public class AddMoveFromNetworkUseCaseImpl implements AddMoveFromNetworkUseCase {

    private final NetworkListenerRepository networkListenerRepository;

    public AddMoveFromNetworkUseCaseImpl(NetworkListenerRepository networkListenerRepository) {
        this.networkListenerRepository = networkListenerRepository;
    }

    @Override
    public void execute(Move move, int gameId) {
        throw new NotImplementedException();
    }
}

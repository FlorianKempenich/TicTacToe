package com.shockn745.application.driving.implementation;

import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.presentation.RegisterNetworkGameListenerUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author Kempenich Florian
 */
public class RegisterNetworkGameListenerUseCaseImplTest {

    RegisterNetworkGameListenerUseCase registerUseCase;

    @Mock
    NetworkListenerRepository repository;

    @Mock
    NetworkListenerRepository.GameNetworkListener listener;
    int gameId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        registerUseCase = new RegisterNetworkGameListenerUseCaseImpl(repository);
    }

    @Test
    public void registerListener_listenerAddedToRepo() throws Exception {
        registerUseCase.registerListener(listener, gameId);
        verify(repository).registerListener(eq(listener), eq(gameId));
    }
}
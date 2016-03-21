package com.shockn745.network;

import com.shockn745.application.driven.NetworkListenerRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kempenich Florian
 */
public class NetworkListenerRepositoryImplTest {

    private final int GAME_ID_1 = 0;
    private final int GAME_ID_2 = 2;
    NetworkListenerRepository repository;
    @Mock
    NetworkListenerRepository.GameNetworkListener listener1;
    @Mock
    NetworkListenerRepository.GameNetworkListener listener2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new NetworkListenerRepositoryImpl();
    }

    @Test
    public void addListener_getList_containsListener() throws Exception {
        repository.registerListener(listener1, GAME_ID_1);

        Set<NetworkListenerRepository.GameNetworkListener> listeners =
                repository.getListeners(GAME_ID_1);

        assertTrue(listeners.contains(listener1));
    }

    @Test
    public void add2TimesSameListener_ignore() throws Exception {
        repository.registerListener(listener1, GAME_ID_1);
        repository.registerListener(listener1, GAME_ID_1);

        Set<NetworkListenerRepository.GameNetworkListener> listeners =
                repository.getListeners(GAME_ID_1);

        assertTrue(listeners.contains(listener1));
        assertEquals(1, listeners.size());
    }

    @Test
    public void add2ListenersDifferentGames_eachSetContainsExpectedListeners() throws Exception {
        repository.registerListener(listener1, GAME_ID_1);
        repository.registerListener(listener2, GAME_ID_2);

        Set<NetworkListenerRepository.GameNetworkListener> listeners1 =
                repository.getListeners(GAME_ID_1);
        Set<NetworkListenerRepository.GameNetworkListener> listeners2 =
                repository.getListeners(GAME_ID_2);

        Set<NetworkListenerRepository.GameNetworkListener> expectedListeners1 = new HashSet<>();
        expectedListeners1.add(listener1);

        Set<NetworkListenerRepository.GameNetworkListener> expectedListeners2 = new HashSet<>();
        expectedListeners2.add(listener2);

        assertEquals(expectedListeners1, listeners1);
        assertEquals(expectedListeners2, listeners2);
    }

    @Test
    public void add2Listeners_removeOne_onlySecondOnePresent() throws Exception {
        // Make expected results
        Set<NetworkListenerRepository.GameNetworkListener> expectedListenersBeforeRemoval = new HashSet<>();
        expectedListenersBeforeRemoval.add(listener1);
        expectedListenersBeforeRemoval.add(listener2);
        Set<NetworkListenerRepository.GameNetworkListener> expectedListenersAfterRemoval = new HashSet<>();
        expectedListenersAfterRemoval.add(listener1);

        // Add 2 listeners
        repository.registerListener(listener1, GAME_ID_1);
        repository.registerListener(listener2, GAME_ID_1);

        Set<NetworkListenerRepository.GameNetworkListener> listenersBeforeRemoval =
                repository.getListeners(GAME_ID_1);

        assertEquals(expectedListenersBeforeRemoval, listenersBeforeRemoval);

        repository.removeListener(listener2, GAME_ID_1);
        Set<NetworkListenerRepository.GameNetworkListener> listenersAfterRemoval =
                repository.getListeners(GAME_ID_1);

        assertEquals(expectedListenersAfterRemoval, listenersAfterRemoval);
    }
}
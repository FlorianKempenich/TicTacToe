package com.shockn745.presentation.other;

import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.implementation.AddMoveFromNetworkUseCaseImpl;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.GameFactory;
import com.shockn745.domain.datamapper.GameMapper;

/**
 * Class to mock a network interaction. Illegal boundary cross but only for integration test
 * purposes.
 * <p/>
 * That is also the reason why the use-case is not injected. The presentation model is not supposed
 * to create instances of the "addMoveFromNetworkUseCase"
 *
 * @author Kempenich Florian
 */
public class FakeMoveFromNetworkGenerator {

    private final AddMoveFromNetworkUseCase addMoveFromNetworkUseCase;

    public FakeMoveFromNetworkGenerator(
            GameStatusRepository gameStatusRepository,
            GameFactory gameFactory,
            NetworkListenerRepository networkListenerRepository) {
        GameMapper gameMapper = new GameMapper(gameFactory);
        this.addMoveFromNetworkUseCase = new AddMoveFromNetworkUseCaseImpl(
                gameStatusRepository,
                networkListenerRepository,
                gameMapper
        );
    }

    public void makeFakeMoveFromNetwork(
            Move move, int gameId,
            AddMoveFromNetworkUseCase.Callback errorCallback) {
        addMoveFromNetworkUseCase.execute(move, gameId, errorCallback);
    }
}

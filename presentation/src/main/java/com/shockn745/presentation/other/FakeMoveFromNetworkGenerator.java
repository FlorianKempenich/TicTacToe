package com.shockn745.presentation.other;

import com.shockn745.application.driven.GameRepository;
import com.shockn745.application.driven.GameStatusRepository;
import com.shockn745.application.driven.NetworkListenerRepository;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.implementation.AddMoveFromNetworkUseCaseImpl;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;
import com.shockn745.domain.GameFactory;

/**
 * @author Kempenich Florian
 *
 * Class to mock a network interaction.
 * Illegal boundary cross but only for integration test purposes.
 *
 * That is also the reason why the use-case is not injected.
 * The presentation model is not supposed to create instances of the "addMoveFromNetworkUseCase"
 */
public class FakeMoveFromNetworkGenerator {

    private final AddMoveFromNetworkUseCase addMoveFromNetworkUseCase;

    public FakeMoveFromNetworkGenerator(GameRepository gameRepository,
            GameFactory gameFactory,
                                        NetworkListenerRepository networkListenerRepository) {
        this.addMoveFromNetworkUseCase = new AddMoveFromNetworkUseCaseImpl(gameRepository, networkListenerRepository, gameFactory);
    }

    public void makeFakeMoveFromNetwork(Move move, int gameId,
                                        AddMoveFromNetworkUseCase.Callback errorCallback) {
        addMoveFromNetworkUseCase.execute(move, gameId, errorCallback);
    }
}

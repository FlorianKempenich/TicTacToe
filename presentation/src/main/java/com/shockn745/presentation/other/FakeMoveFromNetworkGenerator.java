package com.shockn745.presentation.other;

import com.shockn745.application.driving.dto.Move;
import com.shockn745.application.driving.network.AddMoveFromNetworkUseCase;

/**
 * @author Kempenich Florian
 */
public class FakeMoveFromNetworkGenerator {

    private final AddMoveFromNetworkUseCase addMoveFromNetworkUseCase;

    public FakeMoveFromNetworkGenerator(AddMoveFromNetworkUseCase addMoveFromNetworkUseCase) {
        this.addMoveFromNetworkUseCase = addMoveFromNetworkUseCase;
    }

    public void makeFakeMoveFromNetwork(Move move, int gameId) {
        addMoveFromNetworkUseCase.execute(move, gameId);
    }
}

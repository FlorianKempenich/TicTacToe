package com.shockn745.application.driving.network;

import com.shockn745.application.driving.dto.Move;

/**
 * @author Kempenich Florian
 */
public interface AddMoveFromNetworkUseCase {

    // todo init with networkListenerRepository
    void execute(Move move, int gameId);

}

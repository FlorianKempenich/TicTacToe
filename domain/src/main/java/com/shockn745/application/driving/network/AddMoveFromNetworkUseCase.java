package com.shockn745.application.driving.network;

import com.shockn745.application.driving.dto.GameError;
import com.shockn745.application.driving.dto.Move;

/**
 * @author Kempenich Florian
 */
public interface AddMoveFromNetworkUseCase {

    void execute(Move move, int gameId, Callback errorCallback);

    interface Callback {
        void onError(GameError e);
    }

}

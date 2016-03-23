package com.shockn745.domain;

import com.shockn745.application.driving.dto.GameStatus;
import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public interface Game {

    void play(MoveModel move) throws IllegalMoveException;

    boolean checkIfFinishedAndUpdateWinner();

    BoardImpl getBoard();

    int getGameId();

    Player getPreviousPlayer();

    BoardCoordinatesModel getLastSquarePlayed();

    Player getWinner();
}

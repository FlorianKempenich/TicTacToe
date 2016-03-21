package com.shockn745.domain;

import com.shockn745.domain.exceptions.GameNotFinishedException;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public interface Game {

    void play(MoveModel move) throws IllegalMoveException;

    boolean checkIfFinishedAndUpdateWinner();

    com.shockn745.application.driving.Player getWinner() throws GameNotFinishedException;

    com.shockn745.application.driving.GameStatus makeStatus(int id);
}

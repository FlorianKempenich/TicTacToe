package com.shockn745.domain;

import com.shockn745.domain.exceptions.GameNotFinishedException;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public interface Game {

    void play(Move move) throws IllegalMoveException;

    boolean checkIfFinishedAndUpdateWinner();

    Player getWinner() throws GameNotFinishedException;

}

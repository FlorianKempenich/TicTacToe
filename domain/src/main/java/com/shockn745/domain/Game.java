package com.shockn745.domain;

import com.shockn745.application.*;
import com.shockn745.domain.exceptions.GameNotFinishedException;
import com.shockn745.domain.exceptions.IllegalMoveException;

/**
 * @author Kempenich Florian
 */
public interface Game {

    void play(MoveModel move) throws IllegalMoveException;

    boolean checkIfFinishedAndUpdateWinner();

    com.shockn745.application.Player getWinner() throws GameNotFinishedException;

    GameStatus makeStatus(int id);
}

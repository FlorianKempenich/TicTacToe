package com.shockn745.domain;

import com.shockn745.domain.exceptions.IllegalMoveException;
import com.shockn745.domain.iterator.BoardIterator;

/**
 * @author Kempenich Florian
 */
public interface Board {

    void addMove(Move move) throws IllegalMoveException;

    Player getPlayerAtCoordinates(int x, int y);

    BoardIterator getLineIterator(int lineIndex);

    BoardIterator getColumnIterator(int columnIterator);

    BoardIterator getFirstDiagonalIterator();

    BoardIterator getSecondDiagonalIterator();

}

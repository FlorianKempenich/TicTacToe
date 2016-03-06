package com.shockn745.tictactoe.iterator;

import com.shockn745.tictactoe.Player;

public interface BoardIterator {
    boolean hasNext();

    Player next();

    Player first();

}

package com.shockn745.domain.tictac.iterator;

import com.shockn745.domain.tictac.Player;

public interface BoardIterator {
    boolean hasNext();

    Player next();

    Player first();

}

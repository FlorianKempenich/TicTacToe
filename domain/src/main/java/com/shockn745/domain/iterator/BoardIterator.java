package com.shockn745.domain.iterator;

import com.shockn745.domain.Player;

public interface BoardIterator {
    boolean hasNext();

    Player next();

    Player first();

}

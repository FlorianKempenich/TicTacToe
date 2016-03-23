package com.shockn745.domain.iterator;

import com.shockn745.application.driving.dto.Player;
import com.shockn745.domain.Square;

public interface BoardIterator {
    boolean hasNext();

    Square next();

    Square first();

//    void reset(); todo

}

package com.shockn745.domain.factory;

import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;

/**
 * @author Kempenich Florian
 */
public interface MapperFactory {
    CoordinatesMapper coordinatesMapper();

    BoardMapper boardMapper();

    GameMapper gameMapper();
}

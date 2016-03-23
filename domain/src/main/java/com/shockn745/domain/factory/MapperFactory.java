package com.shockn745.domain.factory;

import com.shockn745.domain.MoveModel;
import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;
import com.shockn745.domain.datamapper.MoveMapper;

/**
 * @author Kempenich Florian
 */
public interface MapperFactory {
    CoordinatesMapper coordinatesMapper();

    BoardMapper boardMapper();

    GameMapper gameMapper();

    MoveMapper moveMapper();
}

package com.shockn745.domain.factory;

import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;

/**
 * @author Kempenich Florian
 */
public class MapperFactory {

    public CoordinatesMapper coordinatesMapper() {
        return new CoordinatesMapper();
    }

    public BoardMapper boardMapper() {
        return new BoardMapper();
    }

    public GameMapper gameMapper() {
        return new GameMapper(coordinatesMapper(), boardMapper());
    }

}

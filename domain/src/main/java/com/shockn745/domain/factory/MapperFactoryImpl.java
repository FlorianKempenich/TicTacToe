package com.shockn745.domain.factory;

import com.shockn745.domain.datamapper.BoardMapper;
import com.shockn745.domain.datamapper.CoordinatesMapper;
import com.shockn745.domain.datamapper.GameMapper;

/**
 * @author Kempenich Florian
 */
public class MapperFactoryImpl implements MapperFactory {

    @Override
    public CoordinatesMapper coordinatesMapper() {
        return new CoordinatesMapper();
    }

    @Override
    public BoardMapper boardMapper() {
        return new BoardMapper();
    }

    @Override
    public GameMapper gameMapper() {
        return new GameMapper(coordinatesMapper(), boardMapper());
    }

}

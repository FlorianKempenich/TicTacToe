package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.application.driving.dto.Move;
import com.shockn745.domain.BoardCoordinatesModel;
import com.shockn745.domain.MoveModel;

/**
 * @author Kempenich Florian
 */
public class MoveMapper{

    private final CoordinatesMapper coordinatesMapper;

    public MoveMapper(CoordinatesMapper coordinatesMapper) {
        this.coordinatesMapper = coordinatesMapper;
    }

    public Move transform(MoveModel moveModel) {
        BoardCoordinates coordinates = coordinatesMapper.transform(moveModel.coordinates);
        return new Move(coordinates, moveModel.player);
    }

    public MoveModel transform(Move move) {
        BoardCoordinatesModel coordinatesModel = coordinatesMapper.transform(move.coordinates);
        return new MoveModel(coordinatesModel, move.player);
    }
}

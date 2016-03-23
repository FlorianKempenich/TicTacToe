package com.shockn745.domain.datamapper;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.domain.BoardCoordinatesModel;

/**
 * @author Kempenich Florian
 */
public class CoordinatesMapper {

    public BoardCoordinates transform(BoardCoordinatesModel coordinatesModel) {
        if (coordinatesModel.equals(BoardCoordinatesModel.noCoordinates())) {
            return BoardCoordinates.noCoordinates();
        } else {
            return new BoardCoordinates(coordinatesModel.x, coordinatesModel.y);
        }
    }

    public BoardCoordinatesModel transform(BoardCoordinates coordinates) {
        if (coordinates.equals(BoardCoordinates.noCoordinates())) {
            return BoardCoordinatesModel.noCoordinates();
        } else {
            return BoardCoordinatesModel.fromCoordinates(coordinates.x, coordinates.y);
        }
    }
}

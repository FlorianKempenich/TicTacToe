package com.shockn745.domain;

import com.shockn745.application.driving.dto.BoardCoordinates;
import com.shockn745.domain.exceptions.IllegalCoordinatesException;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class BoardCoordinatesModelTest {

    @Test
    public void createCoordinates_correctValues() throws Exception {
        BoardCoordinates coordinates = new BoardCoordinates(1, 2);

        BoardCoordinatesModel coordinatesModel = new BoardCoordinatesModel(coordinates);

        assertEquals(1, coordinatesModel.x);
        assertEquals(2, coordinatesModel.y);
    }

    @Test (expected = IllegalCoordinatesException.class)
    public void illegalCoordinates_throwException() throws Exception {
        BoardCoordinates coordinates = new BoardCoordinates(-1, 2);
        new BoardCoordinatesModel(coordinates);
    }

    @Test
    public void testEqualityWithNoCoordinates() throws Exception {
        assertEquals(BoardCoordinatesModel.noCoordinates(), BoardCoordinatesModel.noCoordinates());
    }
}
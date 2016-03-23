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
        BoardCoordinatesModel coordinatesModel = BoardCoordinatesModel.fromCoordinates(1, 2);

        assertEquals(1, coordinatesModel.x);
        assertEquals(2, coordinatesModel.y);
    }

    @Test
    public void illegalCoordinates_throwException() throws Exception {
        try {
            BoardCoordinatesModel.fromCoordinates(-1, 2);
            fail();
        } catch (IllegalCoordinatesException e) {
            assertEquals("Out of bounds coordinates", e.getMessage());
        }
    }

    @Test
    public void testEqualityWithNoCoordinates() throws Exception {
        assertEquals(BoardCoordinatesModel.noCoordinates(), BoardCoordinatesModel.noCoordinates());
    }
}
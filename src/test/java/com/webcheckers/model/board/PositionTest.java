package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Position
 */
@Tag("Model-tier")
class PositionTest {

    private static final int ROW = 1;
    private static final int CELL = 2;
    private static final int ROW2 = 3;
    private static final int CELL2 = 4;

    private Position CuT;

    /**
     * Set up a Position to test
     */
    @BeforeEach
    void setup() {
        CuT = new Position(ROW, CELL);
    }

    /**
     * Test getting row
     */
    @Test
    void getRow() {
        assertEquals(CuT.getRow(), ROW, "Position did not return correct row");
    }

    /**
     * Test getting cell
     */
    @Test
    void getCell() {
        assertEquals(CuT.getCell(), CELL, "Position did not return correct cell");
    }

    /**
     * Test equality of Positions
     */
    @Test
    void equals() {
        assertEquals(CuT, CuT, "Position was not equal to itself");

        Object other = 0;
        assertNotEquals(CuT, other, "Position was equal to a non-Position");

        other = new Position(ROW2, CELL);
        assertNotEquals(CuT, other, "Position was equal to a position of different row");

        other = new Position(ROW, CELL2);
        assertNotEquals(CuT, other, "Position was equal to a position of different cell");
    }
}
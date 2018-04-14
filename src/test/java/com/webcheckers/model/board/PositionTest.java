package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class PositionTest {

    private static final int ARG_1 = 1;
    private static final int ARG_2 = 2;


    /**
     * Test that the no-arg constructor works without failure.
     */
    @Test
    void ctor() {
        Position position = new Position(ARG_1, ARG_2);
    }

    @Test
    void getRow() {
        Position position = new Position(ARG_1, ARG_2);
        assertEquals(position.getRow(), ARG_1);
    }

    @Test
    void getCell() {
        Position position = new Position(ARG_1, ARG_2);
        assertEquals(position.getCell(), ARG_2);
    }

}
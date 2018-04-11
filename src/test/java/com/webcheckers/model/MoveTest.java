package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class MoveTest {

    private static final int ROW1 = 1;
    private static final int CELL1 = 2;
    private static final int ROW2 = 1;
    private static final int CELL2 = 2;

    @Test
    void getStart() {
        Position start = new Position(ROW1,CELL1);
        Position end = new Position(ROW2,CELL2);
        Move move = new Move(start, end);
        assertEquals(move.getStart(),start);
    }

    @Test
    void getEnd() {
        Position start = new Position(ROW1,CELL1);
        Position end = new Position(ROW2,CELL2);
        Move move = new Move(start, end);
        assertEquals(move.getEnd(),end);
    }
}
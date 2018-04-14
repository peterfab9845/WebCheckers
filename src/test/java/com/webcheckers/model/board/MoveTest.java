package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class MoveTest {

    private Move CuT;
    private Position start;
    private Position wrongStart;
    private Position end;
    private Position wrongEnd;
    private static final int ROW1 = 1;
    private static final int CELL1 = 2;
    private static final int ROW2 = 1;
    private static final int CELL2 = 2;

    @Test
    void equals() {
        start = mock(Position.class);
        end = mock(Position.class);
        wrongStart = mock(Position.class);
        wrongEnd = mock(Position.class);

        CuT = new Move(start, end);
        Move move = new Move(start, wrongEnd);

        boolean actual = CuT.equals(CuT);
        assertTrue(actual);
        actual = CuT.equals(start);
        assertFalse(actual);
        actual = CuT.equals(move);
        assertFalse(actual);

        move = new Move(wrongStart, end);
        actual = CuT.equals(move);
        assertFalse(actual);

    }

    @Test
    void getStart() {
        Position start = new Position(ROW1, CELL1);
        Position end = new Position(ROW2, CELL2);
        Move move = new Move(start, end);
        assertEquals(move.getStart(), start);
    }

    @Test
    void getEnd() {
        Position start = new Position(ROW1, CELL1);
        Position end = new Position(ROW2, CELL2);
        Move move = new Move(start, end);
        assertEquals(move.getEnd(), end);
    }

}
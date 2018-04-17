package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Move
 */
@Tag("Model-tier")
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

    /**
     * Test the equality of Moves
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void equals() {
        start = mock(Position.class);
        end = mock(Position.class);
        wrongStart = mock(Position.class);
        wrongEnd = mock(Position.class);

        CuT = new Move(start, end);
        Move same = new Move(start, end);
        Move different = new Move(start, wrongEnd);

        assertEquals(CuT, same, "CuT was not equal to another move, same start/end");

        assertNotEquals(CuT, different, "CuT was equal to another move of different end");

        different = new Move(wrongStart, end);

        assertNotEquals(CuT, different, "CuT was equal to another move of different start");
    }

    /**
     * Test getting the start of a Move
     */
    @Test
    void getStart() {
        Position start = mock(Position.class);
        Position end = mock(Position.class);
        Move move = new Move(start, end);

        assertEquals(move.getStart(), start, "Move did not return correct start");
    }

    /**
     * Test getting the end of a Move
     */
    @Test
    void getEnd() {
        Position start = mock(Position.class);
        Position end = mock(Position.class);
        Move move = new Move(start, end);

        assertEquals(move.getEnd(), end, "Move did not return correct end");
    }

}
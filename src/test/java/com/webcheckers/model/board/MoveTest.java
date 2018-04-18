package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
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
     * Set up a move to test
     */
    @BeforeEach
    void setup() {
        start = mock(Position.class);
        end = mock(Position.class);

        CuT = new Move(start, end);
    }
    /**
     * Test the equality of Moves
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void equals() {
        wrongStart = mock(Position.class);
        wrongEnd = mock(Position.class);

        Object other = new Move(start, end);
        assertEquals(CuT, other, "CuT was not equal to another move, same start/end");

        other = 0;
        assertNotEquals(CuT, other, "Move was equal to a non-Move");

        other = new Move(start, wrongEnd);
        assertNotEquals(CuT, other, "CuT was equal to another move of different end");

        other = new Move(wrongStart, end);
        assertNotEquals(CuT, other, "CuT was equal to another move of different start");
    }

    /**
     * Test getting the start of a Move
     */
    @Test
    void getStart() {
        Position start = new Position(ROW1, CELL1); // friendly
        Position end = new Position(ROW2, CELL2);
        CuT = new Move(start, end);

        assertEquals(CuT.getStart(), start, "Move did not return correct start");
        assertEquals(CuT.getStartingX(), start.getCell(),
            "Move returned wrong starting X");
        assertEquals(CuT.getStartingY(), start.getRow(),
            "Move did not return correct starting Y");
    }

    /**
     * Test getting the end of a Move
     */
    @Test
    void getEnd() {
        Position start = new Position(ROW1, CELL1); // friendly
        Position end = new Position(ROW2, CELL2);
        CuT = new Move(end, end);

        assertEquals(CuT.getEnd(), end, "Move did not return correct end");
        assertEquals(CuT.getEndingX(), end.getCell(),
            "Move returned wrong ending X");
        assertEquals(CuT.getEndingY(), end.getRow(),
            "Move did not return correct ending Y");
    }

    /**
     * Test setting/getting the jumped piece
     */
    @Test
    void jumped() {
        Position jumped = new Position(ROW2, CELL2);

        assertNull(CuT.getPieceJumped(), "Move started out with a piece jumped");

        CuT.setJumped(jumped);
        assertEquals(CuT.getPieceJumped(), jumped,
            "Move did not return correct jumped position");
    }

}
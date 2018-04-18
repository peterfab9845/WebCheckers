package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.States.PieceColor;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Row
 */
@Tag("Model-tier")
class RowTest {

    private static int INDEX = 10;
    private static int INDEX2 = 15;
    private Space[] COLS = new Space[8];

    private Row CuT;

    /**
     * Set up a Row to test
     */
    @BeforeEach
    void setup() {
        for (int i = 0; i < 8; i++) {
            COLS[i] = mock(Space.class);
        }
        CuT = new Row(INDEX, COLS, PieceColor.RED);
    }

    /**
     * Test getting the row's index
     */
    @Test
    void getIndex() {
        assertEquals(INDEX, CuT.getIndex(), "Row did not return given index");
    }

    /**
     * Test getting the iterator for Red
     */
    @Test
    void iterator_red() {
        List<Space> colsList = Arrays.asList(COLS);
        Iterator<Space> colsIterator = colsList.iterator();
        Iterator CuTIterator = CuT.iterator();
        colsList.iterator().forEachRemaining(space ->
            assertEquals(space, CuTIterator.next(), "Row's iterator was not correct"));
    }

    /**
     * Test getting the iterator for White
     */
    @Test
    void iterator_white() {
        CuT = new Row(INDEX, COLS, PieceColor.WHITE);
        Deque<Space> colsQueue = new LinkedList<>(Arrays.asList(COLS));
        Iterator<Space> colsIterator = colsQueue.descendingIterator();
        Iterator CuTIterator = CuT.iterator();
        colsIterator.forEachRemaining(space ->
            assertEquals(space, CuTIterator.next(),
                "Row's reverse iterator was not correct"));
    }

    /**
     * Test equality
     */
    @Test
    void equals() {
        Object other = 0;
        assertNotEquals(CuT, other, "Row was equal to non-Row");

        other = new Row(INDEX2, COLS, PieceColor.RED);
        assertNotEquals(CuT, other, "Row was equal to Row of different index");

        other = new Row(INDEX, COLS, PieceColor.WHITE);
        assertNotEquals(CuT, other, "Row was equal to Row with different color");

        other = new Row(INDEX, COLS, PieceColor.RED);
        assertEquals(CuT, other, "Row was not equal to Row of same index and content");
    }
}
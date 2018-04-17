package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;

/**
 * Test class for Board
 */
@Tag("Model-tier")
class BoardTest {

    private Board board;

    /**
     * Create a new board for each test
     */
    @BeforeEach
    void setup() {
        board = new Board();
    }

    /**
     * Test getting the board view
     */
    @Test
    void getBoardView() {
        board.getBoardView(PieceColor.RED);
        board.getBoardView(PieceColor.WHITE);
    }

    /**
     * Test getting pieces on the board
     */
    @Test
    void valueAt() {
        Position position = new Position(0, 1);
        Piece actual = board.valueAt(position);
        Piece expected = new Piece(PieceType.SINGLE, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    /**
     * Test getting the white player's board view
     */
    @Test
    void getBoardViewWhite() {
        BoardView test = board.getBoardView(PieceColor.WHITE);
        Iterator<Row> testIterator = test.iterator();
        Row currentRow;
        int rowIndex = -1;
        while (testIterator.hasNext()) {
            rowIndex++;
            currentRow = testIterator.next();
            Iterator<Space> currentSpaceIterator = currentRow.iterator();
            while (currentSpaceIterator.hasNext()) {
                Space currentSpace = currentSpaceIterator.next();
                if (rowIndex == 0 || rowIndex == 2) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertNotNull(currentSpace.getPiece(), "Red piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    } else {
                        assertTrue(currentSpace.getPiece() == null);
                    }
                } else if (rowIndex == 1) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertNotNull(currentSpace.getPiece(), "Red piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    } else {
                        assertTrue(currentSpace.getPiece() == null);
                    }
                } else if (rowIndex == 5 || rowIndex == 7) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertNotNull(currentSpace.getPiece(), "White piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    } else {
                        assertTrue(currentSpace.getPiece() == null);
                    }
                } else if (rowIndex == 6) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertNotNull(currentSpace.getPiece(), "White piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    } else {
                        assertTrue(currentSpace.getPiece() == null);
                    }
                } else {
                    assertTrue(currentSpace.getPiece() == null);
                }
            }
        }
    }

    /**
     * Test getting the red player's board view
     */
    @Test
    void getBoardViewRed() {
        BoardView test = board.getBoardView(PieceColor.RED);
        Iterator<Row> testIterator = test.iterator();
        Row currentRow;
        int rowIndex = -1;
        while (testIterator.hasNext()) {
            rowIndex++;
            currentRow = testIterator.next();
            Iterator<Space> currentSpaceIterator = currentRow.iterator();
            while (currentSpaceIterator.hasNext()) {
                Space currentSpace = currentSpaceIterator.next();
                if (rowIndex == 0 || rowIndex == 2) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertNotNull(currentSpace.getPiece(), "White piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 1) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertNotNull(currentSpace.getPiece(), "White piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 5 || rowIndex == 7) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertNotNull(currentSpace.getPiece(), "Red piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 6) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertNotNull(currentSpace.getPiece(), "Red piece is null");
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else {
                    assertTrue(currentSpace.getPiece() == null);
                }
            }
        }
    }
}
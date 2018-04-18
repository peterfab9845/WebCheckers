package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.states.PieceType;

/**
 * Test class for Board
 */
@SuppressWarnings({"IfCanBeSwitch", "WhileLoopReplaceableByForEach"})
@Tag("Model-tier")
class BoardTest {

    private Board CuT;

    /**
     * Create a new CuT for each test
     */
    @BeforeEach
    void setup() {
        CuT = new Board();
    }

    /**
     * Test getting pieces on the board
     */
    @Test
    void valueAt() {
        Position position = new Position(0, 1);
        Piece actual = CuT.valueAt(position);
        Piece expected = new Piece(PieceType.SINGLE, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    /**
     * Test getting the white player's board view
     */
    @Test
    void getBoardViewWhite() {
        BoardView test = CuT.getBoardView(PieceColor.WHITE);
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
        BoardView test = CuT.getBoardView(PieceColor.RED);
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

    /**
     * Test getting the board matrix
     */
    @Test
    void getMatrix() {
        Space[][] board = new Space[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Space(col, null);
            }
        }

        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[2][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[6][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
            } else {
                board[1][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[5][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
                board[7][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
            }
        }

        Space[][] returnedBoard = CuT.getMatrix();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                assertEquals(board[row][col], returnedBoard[row][col],
                    "Board did not return correct matrix");
            }
        }
    }

    /**
     * Test clearing a space
     */
    @Test
    void clearSpace() {
        Position position = new Position(0, 1);
        assertNotNull(CuT.valueAt(position), "Test a different space");

        CuT.clearSpace(position);
        assertNull(CuT.valueAt(position), "Space was not correctly cleared");
    }

    /**
     * Test getting piece counts
     */
    @Test
    void getNumPieces() {
        assertEquals(12, CuT.getNumWhitePieces(),
            "Incorrect number of white pieces");
        assertEquals(12, CuT.getNumRedPieces(),
            "Incorrect number of red pieces");
    }
    /**
     * Test removing a piece
     */
    @Test
    void removePiece() {
        // white piece
        int previousCount = CuT.getNumWhitePieces();
        Position position = new Position(0, 1);
        Piece toRemove = CuT.valueAt(position);

        CuT.removePiece(toRemove);
        assertNotEquals(previousCount, CuT.getNumWhitePieces(),
            "White piece was not correctly removed");

        // red piece
        previousCount = CuT.getNumRedPieces();
        position = new Position(7, 0);
        toRemove = CuT.valueAt(position);

        CuT.removePiece(toRemove);
        assertNotEquals(previousCount, CuT.getNumRedPieces(),
            "Red piece was not correctly removed");
    }
}
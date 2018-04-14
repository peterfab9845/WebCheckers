package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board CuT;

    @BeforeEach
    void setup() {
        CuT = new Board();
    }

    @Test
    void getBoardView() {
        CuT.getBoardView(PieceColor.RED);
        CuT.getBoardView(PieceColor.WHITE);
    }

    @Test
    void valueAt() {
        Position position = new Position(0, 1);
        Piece actual = CuT.valueAt(position);
        Piece expected = new Piece(PieceType.SINGLE, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

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
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 1) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 5 || rowIndex == 7) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 6) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else {
                    assertTrue(currentSpace.getPiece() == null);
                }
            }
        }
    }

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
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 1) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 5 || rowIndex == 7) {
                    if (currentSpace.getCellIdx() % 2 == 1) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED
                            && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                } else if (rowIndex == 6) {
                    if (currentSpace.getCellIdx() % 2 == 0) {
                        assertTrue(currentSpace.getPiece() == null);
                    } else {
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
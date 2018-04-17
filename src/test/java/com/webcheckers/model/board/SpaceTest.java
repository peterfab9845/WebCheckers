package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Space
 */
@Tag("Model-tier")
class SpaceTest {


    private final int CELL_IDX = 10;
    private final int CELL_IDX_OTHER = 11;

    private Space CuT;
    private Piece piece;

    /**
     * Set up a Space to test
     */
    @BeforeEach
    void setup() {
        piece = mock(Piece.class);
        CuT = new Space(CELL_IDX, piece);
    }

    /**
     * Test getting cell index
     */
    @Test
    void getCellIdx() {
        assertEquals(CuT.getCellIdx(), CELL_IDX, "Piece did not return correct cell index");
    }

    /**
     * Test getting the piece in the space
     */
    @Test
    void getPiece() {
        assertEquals(CuT.getPiece(), piece, "Space did not return correct piece");
    }

    /**
     * Test space validity (valid if no piece)
     */
    @Test
    void isValid() {
        assertFalse(CuT.isValid(), "Space was valid with a piece");
        
        CuT = new Space(CELL_IDX, null);
        assertTrue(CuT.isValid(), "Space was invalid with no piece");
    }

    /**
     * Test king check
     */
    @Test
    void isKing() {
        when(piece.getType()).thenReturn(PieceType.SINGLE);
        assertFalse(CuT.isKing(), "isKing returned true for non-king piece");

        when(piece.getType()).thenReturn(PieceType.KING);
        assertTrue(CuT.isKing(), "isKing returned false with king piece");

        piece = null;
        assertFalse(CuT.isKing(), "isKing returned true with null piece");
    }

    /**
     * Test equality
     */
    @Test
    void equals() {
        Object other = 0;
        assertNotEquals(CuT, other, "Space was equal to non-Space");

        other = new Space(CELL_IDX_OTHER, piece);
        assertNotEquals(CuT, other, "Space was equal to space of different cell index");

        Piece otherPiece = mock(Piece.class);
        other = new Space(CELL_IDX, otherPiece);
        assertNotEquals(CuT, other, "Space was equal to a space with a different piece");

        other = new Space(CELL_IDX, null);
        assertNotEquals(CuT, other, "Space was equal to a space with no piece");

        other = new Space(CELL_IDX, piece);
        assertEquals(CuT, other, "Space was not equal to a space of same index and piece");
    }
}
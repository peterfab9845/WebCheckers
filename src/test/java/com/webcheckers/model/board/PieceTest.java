package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Piece
 */
@Tag("Model-tier")
class PieceTest {

    private final PieceType TYPE = PieceType.SINGLE;
    private final PieceType TYPE2 = PieceType.KING;
    private final PieceColor COLOR = PieceColor.RED;
    private final PieceColor COLOR2 = PieceColor.WHITE;
    private Piece CuT;

    /**
     * Set up the CuT
     */
    @BeforeEach
    void setup() {
        CuT = new Piece(TYPE, COLOR);
    }

    /**
     * Test getting the type of a piece
     */
    @Test
    void getType() {
        PieceType type = CuT.getType();
        assertEquals(TYPE, type, "Piece did not return correct type");
    }

    /**
     * Test getting the color of a piece
     */
    @Test
    void getColor() {
        PieceColor color = CuT.getColor();
        assertEquals(COLOR, color, "Piece did not return correct color");
    }

    /**
     * Test kinging a piece and getKing()
     */
    @Test
    void king() {
        assertFalse(CuT.isKing(), "Piece was king without being kinged");
        CuT.king();
        assertTrue(CuT.isKing(), "Piece was not king after being kinged");
    }

    /**
     * Test the equality of two Pieces
     */
    @Test
    void equals() {
        Piece test = CuT;
        assertEquals(test, CuT, "Piece was not equal to itself");

        Object other = 0;
        assertNotEquals(CuT, other, "Piece was equal to a non-Piece");

        other = new Piece(TYPE2, COLOR);
        assertNotEquals(other, CuT, "Piece was equal to different type of piece");

        other = new Piece(TYPE, COLOR2);
        assertNotEquals(other, CuT, "Piece was equal to piece of different color");
    }
}
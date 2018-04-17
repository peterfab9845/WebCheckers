package com.webcheckers.model.States;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for PieceType
 */
@Tag("Model-tier")
class PieceTypeTest {

    /**
     * Test SINGLE value
     */
    @Test
    void SINGLE_test() {
        PieceType play = PieceType.SINGLE;
        assertEquals(play, PieceType.valueOf("SINGLE"));
    }

    /**
     * Test KING value
     */
    @Test
    void KING_test() {
        PieceType play = PieceType.KING;
        assertEquals(play, PieceType.valueOf("KING"));
    }

}

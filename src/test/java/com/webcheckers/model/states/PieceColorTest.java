package com.webcheckers.model.states;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for PieceColor
 */
@Tag("Model-tier")
class PieceColorTest {

    /**
     * Test RED value
     */
    @Test
    void RED_test() {
        PieceColor play = PieceColor.RED;
        assertEquals(play, PieceColor.valueOf("RED"));
    }

    /**
     * Test WHITE value
     */
    @Test
    void WHITE_test() {
        PieceColor play = PieceColor.WHITE;
        assertEquals(play, PieceColor.valueOf("WHITE"));
    }    
}

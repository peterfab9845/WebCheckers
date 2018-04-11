package com.webcheckers.model.board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    private final PieceType TYPE = PieceType.SINGLE;
    private final PieceColor COLOR = PieceColor.RED;
    private Piece CuT;

    @BeforeEach
    void setup(){
        CuT = new Piece(TYPE, COLOR);
    }

    @Test
    void getType() {
        PieceType type = CuT.getType();
        assertEquals(TYPE, type);
    }

    @Test
    void getColor() {
        PieceColor color = CuT.getColor();
        assertEquals(COLOR, color);
    }

    @Test
    void equals() {
        Piece test = CuT;
        assertTrue(test.equals(CuT));
    }
}
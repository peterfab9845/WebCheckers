package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PieceTest {


    private static final PieceType PIECE_TYPE = PieceType.SINGLE;
    private static final PieceColor PIECE_COLOR = PieceColor.RED;

    @Test
    void getType() {
        Piece piece = new Piece(PIECE_TYPE, PIECE_COLOR);
        assertEquals(piece.getType(), PIECE_TYPE);
    }

    @Test
    void getColor() {
        Piece piece = new Piece(PIECE_TYPE, PIECE_COLOR);
        assertEquals(piece.getColor(), PIECE_COLOR);
    }
}
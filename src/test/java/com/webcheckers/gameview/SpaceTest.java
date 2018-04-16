//package com.webcheckers.gameview;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//@Tag("Model-tier")
//class SpaceTest {
//
//
//    private final int CELLIDX = 10;
//    private PieceType TYPE = PieceType.KING;
//    private PieceColor COLOR = PieceColor.WHITE;
//
//    private Space space;
//    private Piece piece;
//
//    @BeforeEach
//    void setup(){
//        piece = new Piece(TYPE, COLOR);
//        space = new Space(CELLIDX, piece);
//    }
//
//    @Test
//    void getCellIdx() {
//        assertEquals(space.getCellIdx(), CELLIDX);
//    }
//
//    @Test
//    void isValid() {
//        assertTrue(space.isValid());
//    }
//
//    @Test
//    void getPiece() {
//        assertEquals(space.getPiece(), piece);
//    }
//}
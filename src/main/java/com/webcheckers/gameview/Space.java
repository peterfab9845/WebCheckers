package com.webcheckers.gameview;

public class Space {

    private int cellIdx;
    private Piece piece;

    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
        return true;
    }

    public Piece getPiece() {
        return piece;
    }
}

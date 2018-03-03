package com.webcheckers.gameview;

public class Space {

    private int cellIdx;
    private Piece piece;

    public int getCellId() {
        return cellIdx;
    }

    public boolean isValid(){
        return true;
    }

    public Piece getPiece(){
        return piece;
    }
}

package com.webcheckers.gameview;

public class Space {

    private int cellId;
    private Piece piece;

    public int getCellId() {
        return cellId;
    }

    public boolean isValid(){
        return true;
    }

    public Piece getPiece(){
        return piece;
    }
}

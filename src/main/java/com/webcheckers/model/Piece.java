package com.webcheckers.model;

import com.webcheckers.model.PieceColor;
import com.webcheckers.model.PieceType;

public class Piece {

    private PieceType type;
    private PieceColor color;

    public Piece( PieceType type, PieceColor color ){
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}

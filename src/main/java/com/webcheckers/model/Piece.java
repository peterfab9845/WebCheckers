package com.webcheckers.model;

/**
 * A checkers piece
 */
public class Piece {

    /**
     * The type of piece this is
     */
    private PieceType type;

    /**
     * The color of this piece
     */
    private PieceColor color;

    /**
     * Create a piece of the given type and color
     * @param type the type of piece to create
     * @param color the color of piece to create
     */
    public Piece( PieceType type, PieceColor color ){
        this.type = type;
        this.color = color;
    }

    /**
     * Get the type of this piece
     * @return the type of this piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Get the color of this piece
     * @return the color of this piece
     */
    public PieceColor getColor() {
        return color;
    }
}

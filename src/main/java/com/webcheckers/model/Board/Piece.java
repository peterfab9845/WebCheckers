package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;

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
    public Piece(PieceType type, PieceColor color ){
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

    /**
     * returns wether two objects are equal
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Piece){
            Piece otherPiece = (Piece)obj;
            if (otherPiece.getColor() == this.color){
                if (otherPiece.getType() == this.type) return true;
            }
        }
        return false;
    }

    public boolean isKing() {
        return this.type == PieceType.KING;
    }

    public void king() {
        this.type = PieceType.KING;
    }
}

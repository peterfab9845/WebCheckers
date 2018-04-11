package com.webcheckers.model.board;

import com.webcheckers.model.States.PieceType;

public class Space {

    /**
     * The index of this space in its row
     */
    private int cellIdx;

    /**
     * The piece in this space, if any
     */
    private Piece piece;

    /**
     * Create a Space with specified index, containing given piece
     * @param cellIdx the index of this Space
     * @param piece the piece to go in this Space
     */
    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    /**
     * Get the index of this Space
     * @return the index of this Space
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Get the piece in this space
     * @return the piece in this space
     */
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Get whether or not this is a valid space to move to
     * @return true if this is a valid space to move to
     */
    public boolean isValid() {
        return piece == null;
    }

    public boolean isKing(){
        return piece != null && piece.getType() == PieceType.KING;
    }


    /**
     * returns wether two objects are equal
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Space){
            Space otherSpace = (Space)obj;
            if (otherSpace.getCellIdx() == this.cellIdx)
                return (otherSpace.getPiece()).equals(this.piece);
        }
        return false;
    }
}

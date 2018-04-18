package com.webcheckers.model.board;

/**
 * A move from the player.
 * 
 * @author Adam Heeter
 */
public class Move {

    /**
     * The starting position of the move
     */
    private Position start;

    /**
     * The ending position of the move
     */
    private Position end;

    /**
     * Piece jumped
     */
    private Position pieceJumped;

    //private LinkedList<Space> spacesJumped;

    /**
     * Create a Move with the given starting and ending positions
     * @param start the Position where the move is from
     * @param end the Position which the move is to
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        //spacesJumped = new LinkedList<>();
    }

    /**
     * Get the starting position of this move
     * @return the starting position of this move
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get the ending position of this move
     * @return the ending position of this move
     */
    public Position getEnd() {
        return end;
    }

    public int getStartingX(){
        return start.getCell();
    }

    public int getEndingX(){
        return end.getCell();
    }

    public int getStartingY(){
        return start.getRow();
    }

    public int getEndingY(){
        return end.getRow();
    }

    /**
     * Returns if two objects are equal
     * @param obj the object to check against
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            if (start.equals(((Move) obj).getStart())) {
                return end.equals(((Move) obj).getEnd());
            }
        }
        return false;
    }

    public void setJumped(Position jumped) {
        this.pieceJumped = jumped;
    }

    public Position getPieceJumped(){
        return this.pieceJumped;
    }

    @Override
    public String toString() {
        return start + "," + end;
    }
}
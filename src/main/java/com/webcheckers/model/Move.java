package com.webcheckers.model;

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
     * Create a Move with the given starting and ending positions
     * @param start the Position where the move is from
     * @param end the Position which the move is to
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
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
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) {
            return false;
        } else {
            return start.equals(((Move) obj).start) && end.equals(((Move) obj).end);
        }
    }
}
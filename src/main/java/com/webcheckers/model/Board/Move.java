package com.webcheckers.model.Board;

import java.util.Iterator;
import java.util.LinkedList;

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

    private LinkedList<Space> spacesJumped;

    /**
     * Create a Move with the given starting and ending positions
     * @param start the Position where the move is from
     * @param end the Position which the move is to
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        spacesJumped = new LinkedList<>();
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Move && ((Move) obj).start == this.start && ((Move) obj).end == this.end;
    }

//    public Iterator iterator(){
//        return spacesJumped.iterator();
//    }
}
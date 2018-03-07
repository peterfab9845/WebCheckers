package com.webcheckers.model;

/**
 * A move from the player.
 * 
 * @author Adam Heeter
 */
public class Move {
    private Position start;
    
    private Position end;
    
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }
    
    public Position getStart() {
        return start;
    }
    
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
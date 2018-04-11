package com.webcheckers.model.board;

import com.webcheckers.model.States.PieceColor;

import java.util.*;

/**
 * The Row class for sending to the client
 */
public class Row implements Iterable {

    /**
     * The index of this row in the board
     */
    private int index;

    /**
     * The list of spaces in this row
     */
    private List<Space> spaces;

    private PieceColor color;

    /**
     * Create a row with an index, based on an array of Pieces
     * @param index the index of the row
     * @param boardCols array of the pieces in the row
     */
    public Row(int index, Space[] boardCols, PieceColor color) {
        spaces = new ArrayList<>();
        this.color = color;
        this.index = index;
        Collections.addAll(spaces, boardCols);
    }

    /**
     * Get the index of this row
     * @return the index of this row
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get an iterator over the pieces in this row
     * @return an iterator over the pieces in this row
     */
    @Override
    public Iterator<Space> iterator() {
        if( color.equals(PieceColor.RED) )
            return spaces.iterator();
        return reverseIterator();
    }

    private Iterator<Space> reverseIterator(){
        color = PieceColor.RED;
        Stack<Space> stack = new Stack<Space>();
        Stack<Space> reverseStack = new Stack<Space>();
        for (Object o : this)
            stack.push((Space) o);
        while(!stack.isEmpty())
            reverseStack.push(stack.pop());
        color = PieceColor.WHITE;
        return reverseStack.iterator();
    }


    /**
     * returns weather two objects are equal
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Row) {
            Row otherRow = (Row) obj;
            if (otherRow.getIndex() == this.index)
                return (otherRow.iterator()).equals(this.iterator());
        }
        return false;
    }
}
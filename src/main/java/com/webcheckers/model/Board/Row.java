package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

    /**
     * Create a row with an index, based on an array of Pieces
     * @param index the index of the row
     * @param boardCols array of the pieces in the row
     */
    public Row(int index, Space[] boardCols) {
        spaces = new ArrayList<>();
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
        return spaces.iterator();
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

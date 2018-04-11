package com.webcheckers.gameview;

import com.webcheckers.model.Piece;
import java.util.ArrayList;
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
    public Row(int index, Piece[] boardCols) {
        spaces = new ArrayList<>(8);
        this.index = index;
        for (int col = 0; col < boardCols.length; col++) {
            spaces.add(new Space(col, boardCols[col]));
        }
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

    @Override
    public boolean equals(Object other){
        if (other instanceof Row) {
            Row otherRow = (Row) other;
            if (otherRow.getIndex() == this.index);{
                return (otherRow.iterator()).equals(this.iterator());
            }
        }
        return false;
    }
}

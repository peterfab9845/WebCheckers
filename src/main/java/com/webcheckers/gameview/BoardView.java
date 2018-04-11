package com.webcheckers.gameview;

import com.webcheckers.model.Piece;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The class representation for transmission to the client
 */
public class BoardView implements Iterable<Row> {

    /**
     * The list of Rows in the board
     */
    private List<Row> rows;

    /**
     * Creates a BoardView from the 2D array representation in Board
     * @param boardRows 2D array of Pieces to convert
     */
    public BoardView(Piece[][] boardRows) {
        rows = new ArrayList<>(8);
        for (int row = 0; row < boardRows.length; row++) {
            rows.add(new Row(row, boardRows[row]));
        }
    }

    /**
     * Get the Iterator of the rows in this BoardView
     * @return Iterator of the rows in this BoardView
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof BoardView){
            BoardView otherBoardView = (BoardView)other;
            Iterator<Row> otherIterator = otherBoardView.iterator();
            Iterator<Row> thisIterator = iterator();
            while (otherIterator.hasNext()){
                Row otherRow = otherIterator.next();
                Row thisRow = thisIterator.next();
                if (!otherRow.equals(thisRow)) return false;
            }
        }
        return false;
    }
}

package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;

import static java.util.Collections.*;

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
    public BoardView(Space[][] boardRows) {
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

}

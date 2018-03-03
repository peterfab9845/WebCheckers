package com.webcheckers.gameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

    private List<Row> rows;

    public BoardView(Piece[][] boardRows) {
        rows = new ArrayList<>(8);
        for (int row = 0; row < boardRows.length; row++) {
            rows.add(new Row(row, boardRows[row]));
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}

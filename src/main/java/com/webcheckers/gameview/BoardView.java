package com.webcheckers.gameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

    private List<Row> rows;

    public BoardView() {
        this.rows = new ArrayList<>(8);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}

package com.webcheckers.gameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable {

    private int index;
    private List<Space> spaces;

    public Row(int index, Piece[] boardCols) {
        spaces = new ArrayList<>(8);
        this.index = index;
        for (int col = 0; col < boardCols.length; col++) {
            spaces.add(new Space(col, boardCols[col]));
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}

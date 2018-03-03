package com.webcheckers.gameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable {

    private int index;
    private List<Space> spaces;

    public Row(){
        spaces = new ArrayList<>(8);
    }

    public int getIndex(){
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}

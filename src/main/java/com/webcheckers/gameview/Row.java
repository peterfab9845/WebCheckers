package com.webcheckers.gameview;

public class Row {

    private int index;
    private Space[] spaces;

    public Row(){
        spaces = new Space[8];
    }

    public int getIndex(){
        return index;
    }
}

package com.webcheckers.model;

/**
 * A row, cell position.
 * 
 * @author Adam Heeter
 */
public class Position {
    private int row;
    
    private int cell;
    
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCell() {
        return cell;
    }

}
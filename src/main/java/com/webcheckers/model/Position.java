package com.webcheckers.model;

/**
 * A row, cell position.
 * 
 * @author Adam Heeter
 */
public class Position {

    /**
     * The row of this position
     */
    private int row;

    /**
     * The cell of this position
     */
    private int cell;

    /**
     * Create a new Position with the given row and cell indices
     * @param row the row index of the Position
     * @param cell the cell index of the Position
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Get the row index of this Position
     * @return the row index of this position
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the cell index of this Position
     * @return the Cell index of this Position
     */
    public int getCell() {
        return cell;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Position){
            Position otherPosition = (Position)other;
            if (otherPosition.getCell() == this.cell){
                if (otherPosition.getRow() == this.row) return true;
            }
        }
        return false;
    }

}
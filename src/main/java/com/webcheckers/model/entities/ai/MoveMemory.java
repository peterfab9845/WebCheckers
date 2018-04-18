package com.webcheckers.model.entities.ai;

import com.webcheckers.model.board.Move;

public class MoveMemory{

    public String matrix;
    public Move move;
    public int wonIn;


    public boolean hasBeenSaved;


    public MoveMemory(String matrix, Move move){
        this.matrix = matrix;
        this.move = move;
        hasBeenSaved = false;
    }

    public void setWonIn(int wonIn) {
        this.wonIn = wonIn;
    }
    public int getWonIn() {
        return wonIn;
    }

    @Override
    public String toString() {
        return matrix + move + "," + wonIn;
    }

    public Move getMove() {
        return move;
    }

    public boolean hasBeenSaved() {
        return hasBeenSaved;
    }

    public void setHasBeenSaved(boolean hasBeenSaved) {
        this.hasBeenSaved = hasBeenSaved;
    }
}
package com.webcheckers.model.entities.ai;

import com.webcheckers.model.board.Move;

public class MoveMemory{

    public String matrix;
    public Move move;
    public int wonIn;


    public MoveMemory(String matrix, Move move){
        this.matrix = matrix;
        this.move = move;
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
}

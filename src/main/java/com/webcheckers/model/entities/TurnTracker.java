package com.webcheckers.model.entities;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;

import java.util.LinkedList;

public class TurnTracker extends LinkedList{

    /**
     * Games Board
     */
    private Board board;

    /**
     * Constructor
     * @param board
     */
    public TurnTracker(Board board){
        super();
        this.board = board;
    }

    /**
     * finishes the turn by sending all the move requests
     */
    public void finalizeTurn(){
        while(!isEmpty()) {
            Move move = (Move) remove();
            board.makeMove(move);
        }
    }
}

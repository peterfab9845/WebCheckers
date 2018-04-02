package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Move;
import com.webcheckers.ui.Movement.PostValidateMoveRoute;

import java.util.LinkedList;
import java.util.logging.Logger;

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

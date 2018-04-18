package com.webcheckers.model.entities;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;

import java.util.LinkedList;

public class TurnTracker extends LinkedList{

    /**
     * Games Board
     */
    private Game game;

    /**
     * Constructor
     * @param board
     */
    public TurnTracker(Game game){
        super();
        this.game = game;
    }

    /**
     * finishes the turn by sending all the move requests
     */
    public void finalizeTurn(){
        while(!isEmpty()) {
            Move move = (Move) remove();
            BoardController.makeMove(game, move);
        }
    }
}

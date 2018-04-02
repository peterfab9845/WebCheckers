package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Move;
import com.webcheckers.ui.Movement.PostValidateMoveRoute;

import java.util.LinkedList;
import java.util.logging.Logger;

public class MoveTracker extends LinkedList{

    private Board board;

    public MoveTracker(Board board){
        super();
        this.board = board;
    }

    public void addMove(Move move){
        super.add(move);
    }

    private static final Logger LOG = Logger.getLogger(MoveTracker.class.getName());

    public void finalizeTurn(){
        while(!isEmpty()) {
            Move move = (Move) remove();
            LOG.info("Moved: (" + move.getStart().getCell() + ", "+ move.getStart().getRow() +"), (" + move.getEnd().getCell() + ", "+ move.getEnd().getRow() +")");
            board.makeMove(move);
        }
    }
}

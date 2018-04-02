package com.webcheckers.model.Board;


public class MoveChecker {

    private Board board;

    public MoveChecker( Board board ){
        this.board = board;
    }

    public boolean validSpace(Position position){
        return board.valueAt(position) == null;
    }

}

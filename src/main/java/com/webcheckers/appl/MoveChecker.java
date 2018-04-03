package com.webcheckers.appl;

import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Position;

public class MoveChecker {

    public static boolean isMoveValid(Move move){
        if( !positionIsBlack(move.getEnd()) )
            return false;
        return true;
    }

    private static boolean positionIsBlack(Position position){
        if( position.getRow() % 2 == 0)
            return position.getCell() % 2 == 1;
        else
            return position.getCell() % 2 == 0;
    }

//    public boolean validSpace(Position position){
//        return board.valueAt(position) == null;
//    }
}

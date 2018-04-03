package com.webcheckers.appl;

import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.Position;
import com.webcheckers.model.Board.Space;

public class MoveChecker {

    private static final int SINGLE_DISTANCE = 2;
    private static final int KING_DISTANCE = 3;


    public static boolean hasValidMove(Position position, Space[][] board){
        int startX = position.getCell();
        int startY = position.getRow();
        Position placeInQuestion;
        Move move;
        for( int row = -1; row < 2; row+=2){
            for( int col = -1; col < 2; col+=2) {
                placeInQuestion = new Position(startY + row, startX + col);
                move = new Move(position, placeInQuestion);
                if(isMoveValid(move, board))
                    return true;
            }
        }
        return false;
    }


    public static boolean isMoveValid(Move move, Space[][] board){
        if( !positionIsBlack(move.getEnd()) )
            return false;
        if( !inDistanceOf(move, SINGLE_DISTANCE) )
            return false;


        return true;
    }

    private static boolean positionIsBlack(Position position){
        if( position.getRow() % 2 == 0)
            return position.getCell() % 2 == 1;
        else
            return position.getCell() % 2 == 0;
    }

    private static boolean inDistanceOf(Move move, int dist){
        int x1 = move.getStartingX();
        int x2 = move.getEndingX();
        int deltaX = x1 - x2;
        int y1 = move.getStartingY();
        int y2 = move.getEndingY();
        int deltaY = y1 - y2;
        if( deltaX == 0 )
            return false;
        int distance = deltaY/ deltaX;
        return Math.abs(distance) < dist;
    }


    private static Piece pieceAt(Position position, Space[][] board){
        int x = position.getCell();
        int y = position.getRow();
        return board[y][x].getPiece();
    }
}

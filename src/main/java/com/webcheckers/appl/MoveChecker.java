package com.webcheckers.appl;

import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.Position;
import com.webcheckers.model.Board.Space;
import com.webcheckers.model.States.PieceColor;

public class MoveChecker {

    private static final int SINGLE_DISTANCE = 1;
    private static final int KING_DISTANCE = 3;


    public static boolean hasValidMove(Position position, Space[][] board, PieceColor color){
        int startX = position.getCell();
        int startY = position.getRow();
        Position placeInQuestion;
        Move move;
        for( int row = -1; row < 2; row+=2){
            for( int col = -1; col < 2; col+=2) {
                placeInQuestion = new Position(startY + row, startX + col);
                move = new Move(position, placeInQuestion);
                if(isMoveValid(move, board, color))
                    return true;
            }
        }
        return false;
    }


    public static boolean isMoveValid(Move move, Space[][] board, PieceColor color){

        //Check for black space
        if( !positionIsBlack(move.getEnd()) )
            return false;

        //Check for direction
        if(color == PieceColor.RED && delta( move.getStartingY(), move.getEndingY() ) < 0 )
            return false;
        if(color == PieceColor.WHITE && delta( move.getStartingY() , move.getEndingY()) > 0 )
            return false;

//        if( !inDistanceOf(move, SINGLE_DISTANCE) )
//            return false;

        return true;
    }

    private static boolean positionIsBlack(Position position){
        if( position.getRow() % 2 == 0)
            return position.getCell() % 2 == 1;
        else
            return position.getCell() % 2 == 0;
    }

    private static boolean inDistanceOf(Move move, int dist) {
        int deltaX = delta(move.getStartingX(), move.getEndingX());
        int deltaY = delta(move.getEndingX(), move.getEndingY());
        return Math.abs(deltaX) == dist && Math.abs(deltaY) == dist;
    }

    private static int delta(int value1,int value2){
        return value1 - value2;
    }

    private static Piece pieceAt(Position position, Space[][] board){
        int x = position.getCell();
        int y = position.getRow();
        return board[y][x].getPiece();
    }
}

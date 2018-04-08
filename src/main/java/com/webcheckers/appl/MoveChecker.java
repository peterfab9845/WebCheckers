package com.webcheckers.appl;

import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.Position;
import com.webcheckers.model.Board.Space;
import com.webcheckers.model.States.PieceColor;
import com.webcheckers.ui.Game.GetGameRoute;
import javafx.geometry.Pos;

import java.util.logging.Logger;

public class MoveChecker {

    private static final int SINGLE_DISTANCE = 1;
    private static final int JUMP_DISTANCE = 2;
    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());



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

        if(!positionOnBoard(move.getEnd()))
            return false;

        //Check for black space
        if( !positionIsBlack(move.getEnd()) )
            return false;

        if( !isKing(move.getStart(), board) ){
            //Check for direction
            if(color == PieceColor.RED && movingNorth(move) )
                return false;
            if(color == PieceColor.WHITE && movingSouth(move) )
                return false;
            LOG.info("valid direction");
        }

        if( inDistanceOf(move, JUMP_DISTANCE)) {
            if ( hasPieceBetween(move, board) && pieceBetween(move, board).getColor() != color) {
                move.setJumped(positionBetween(move));
            }
            else{
                LOG.info("did not have a piece between");
                return false;
            }
        }
        else if( !inDistanceOf(move, SINGLE_DISTANCE) ){
            LOG.info("Not within one move");
            return false;
        }

        return true;
    }

    private static boolean positionOnBoard(Position position) {
        return position.getCell() >= 0 && position.getRow() >= 0 && position.getCell() <= 8 && position.getRow() <= 8;
    }

    private static boolean positionIsBlack(Position position){
        if( position.getRow() % 2 == 0)
            return position.getCell() % 2 == 1;
        else
            return position.getCell() % 2 == 0;
    }

    private static boolean hasPieceBetween(Move move, Space[][] board) {
        return pieceBetween(move,board) != null;
    }

    private static Piece pieceBetween(Move move, Space[][] board) {
        Position position = positionBetween(move);
        return board[position.getRow()][position.getCell()].getPiece();
    }

    private static Position positionBetween(Move move){
        int x= (move.getStartingX() + move.getEndingX()) / 2;
        int y = (move.getStartingY() + move.getEndingY()) / 2;
        return new Position(y, x);
    }

    private static boolean inDistanceOf(Move move, int dist) {
        int deltaX = delta(move.getStartingX(), move.getEndingX());
        int deltaY = delta(move.getStartingY(), move.getEndingY());
        return Math.abs(deltaX) == dist && Math.abs(deltaY) == dist;
    }

    private static int delta(int value1,int value2){
        return value1 - value2;
    }

    private static boolean isKing(Position position, Space[][] board){
        int x = position.getCell();
        int y = position.getRow();
        return board[y][x].isKing();
    }

    private static boolean movingNorth(Move move){
        return delta( move.getStartingY(), move.getEndingY() ) < 0;
    }

    private static boolean movingSouth(Move move){
        return delta( move.getStartingY(), move.getEndingY() ) > 0;
    }

}

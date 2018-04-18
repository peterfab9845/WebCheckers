package com.webcheckers.appl;

import com.webcheckers.model.board.*;
import com.webcheckers.model.states.PieceColor;
import com.webcheckers.ui.game.GetGameRoute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class MoveChecker {

    private static final int SINGLE_DISTANCE = 1;
    private static final int JUMP_DISTANCE = 2;
    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private static boolean pieceMoved = false;

    public static boolean playerHasValidMove(Board board, PieceColor color){
        ArrayList<Position> positionsOfPieces = board.getLocationOfPieces(color);
        for (Position position : positionsOfPieces) {
            if (hasValidMove(position, board, color)) return true;
        }
        return false;
    }

    public static boolean hasValidMove(Position position, Board board, PieceColor color){
        if( position == null )
            return false;
        int startX = position.getCell();
        int startY = position.getRow();
        Position placeInQuestion;
        Move move;
        for( int row = -6; row < 7; row+=1){
            for( int col = -6; col < 7; col+=1) {
                placeInQuestion = new Position(startY + row, startX + col);
                move = new Move(position, placeInQuestion);
                boolean isKing = isKing(move.getStart(), board.getMatrix());
                if(isMoveValid(move, board, color, isKing,true))
                    return true;
            }
        }
        return false;
    }

    public static boolean isMoveValid(Move move, Board board, PieceColor color, boolean king){

        if(move == null)
            return false;

        if(!positionOnBoard(move.getEnd()))
            return false;

        if(board.getMatrix()[move.getEndingY()][move.getEndingX()].getPiece() != null )
            return false;

        //Check for black space
        if( !positionIsBlack(move.getEnd()) )
            return false;

        if(!checks(move, board.getMatrix(), color, king)){
            return false;
        }

        if(forsedMoveAvalible(board, color) && !inDistanceOf(move, JUMP_DISTANCE))
            return false;

        pieceMoved = true;
        return true;
    }

    public static boolean isMoveValid(Move move, Board board, PieceColor color, boolean king, boolean isTesting){

        if(move == null)
            return false;

        if(!positionOnBoard(move.getEnd()))
            return false;

        if(board.getMatrix()[move.getEndingY()][move.getEndingX()].getPiece() != null )
            return false;

        //Check for black space
        if( !positionIsBlack(move.getEnd()) )
            return false;

        if(!checks(move, board.getMatrix(), color, king)){
            return false;
        }

        if(forsedMoveAvalible(board, color) && !inDistanceOf(move, JUMP_DISTANCE))
            return false;

        if(isTesting)
            pieceMoved = false;
        return true;
    }

    private static boolean forsedMoveAvalible(Board board, PieceColor color){
        ArrayList<Position> positionsOfPieces = board.getLocationOfPieces(color);
        for (Position position : positionsOfPieces) {
            if (hasJumpMove(position, board, color)) return true;
        }
        return false;
    }

    private static boolean hasJumpMove(Position position, Board board, PieceColor color) {
        if( position == null )
            return false;
        int startX = position.getCell();
        int startY = position.getRow();

        Move move;
        Position placeInQuestion;
        for( int row = -6; row < 7; row+=1){
            for( int col = -6; col < 7; col+=1) {
                placeInQuestion = new Position(startY + row, startX + col);
                move = new Move(position, placeInQuestion);
                boolean isKing = isKing(move.getStart(), board.getMatrix());

                if( !isKing ){
                    //Check for direction
                    if(color == PieceColor.RED && movingNorth(move) )
                        continue;
                    if(color == PieceColor.WHITE && movingSouth(move) )
                        continue;
                }

                if(positionOnBoard(move.getEnd())) {
                    if (inDistanceOf(move, JUMP_DISTANCE)) {
                        if (hasPieceBetween(move, board.getMatrix()) && pieceBetween(move, board.getMatrix()).getColor() != color) {
                            if(board.getMatrix()[move.getEndingY()][move.getEndingX()].getPiece() == null)
                                return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private static boolean checks(Move move, Space[][] board, PieceColor color, boolean king){
        if( !king ){
            //Check for direction
            if(color == PieceColor.RED && movingNorth(move) )
                return false;
            if(color == PieceColor.WHITE && movingSouth(move) )
                return false;
            if(pieceMoved)
                return false;
        }

        if( inDistanceOf(move, JUMP_DISTANCE) ) {
            if ( hasPieceBetween(move, board) && pieceBetween(move, board).getColor() != color) {
                move.setJumped(positionBetween(move));
            }
            else{
                return false;
            }
        }
        else if( !inDistanceOf(move, SINGLE_DISTANCE) ) {
            return false;
        }

        return true;
    }

    private static boolean positionOnBoard(Position position) {
        return position.getCell() >= 0 && position.getRow() >= 0 && position.getCell() < 8 && position.getRow() < 8;
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

    public static boolean isKing(Position position, Space[][] board){
        if(position == null)
            return false;

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

    public static void resetPieceMoved(){
        pieceMoved = false;
    }
}

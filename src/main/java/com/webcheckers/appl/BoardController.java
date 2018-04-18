package com.webcheckers.appl;

import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.states.PieceType;

import java.util.LinkedList;

public class BoardController {

    public static void initBoard(Space[][] board, LinkedList<Piece> redPieces, LinkedList<Piece> whitePieces){

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Space(col, null);
            }
        }

        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                whitePieces.add(board[0][col].getPiece());
                board[2][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                whitePieces.add(board[2][col].getPiece());
                board[6][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
                redPieces.add(board[6][col].getPiece());
            } else {
                board[1][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                whitePieces.add(board[1][col].getPiece());
                board[5][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
                redPieces.add(board[5][col].getPiece());
                board[7][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
                redPieces.add(board[7][col].getPiece());
            }
        }

    }

    public static void makeMove(Game game, Move move){
        Board board = game.getBoard();
        Position start = move.getStart();
        Position end = move.getEnd();
        Space[][] spaces = board.getMatrix();
        Piece piece = spaces[start.getRow()][start.getCell()].getPiece();
        spaces[start.getRow()][start.getCell()].setPiece(null);

        if (piece != null) {
            if (end.getRow() == 0 && piece.getColor() == PieceColor.RED)
                piece.king();
            if (end.getRow() == 7 && piece.getColor() == PieceColor.WHITE)
                piece.king();
        }

        spaces[end.getRow()][end.getCell()].setPiece(piece);
        removePiece(board, move.getPieceJumped());
    }

    private static void removePiece(Board board, Position position) {
        if( position == null )
            return;
        Piece piece = board.valueAt(position);
        board.removePiece(piece);
        board.clearSpace(position);
    }


    public static Position getPieceLocation(Space[][] board, Piece piece) {
        for(int i = 0; i < board.length; i++){
            for( int j = 0; j < board[i].length; j++){
                if(board[i][j].getPiece() == piece){
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

}

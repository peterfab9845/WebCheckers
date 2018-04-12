package com.webcheckers.appl;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import com.webcheckers.model.board.*;

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

    public static void makeMove(Board board, Move move){
        Position start = move.getStart();
        Position end = move.getEnd();
        Space[][] spaces = board.getMatrix();
        Piece piece = spaces[start.getRow()][start.getCell()].getPiece();
        spaces[start.getRow()][start.getCell()].setPiece(null);

        if(end.getRow() == 0 && piece != null && piece.getColor() == PieceColor.RED)
            piece.king();
        if(end.getRow() == 7 && piece != null && piece.getColor() == PieceColor.WHITE)
            piece.king();

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


}

package com.webcheckers.model.board;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.states.PieceColor;

import java.util.LinkedList;

public class Board {

    /**
     * The 2D array of pieces in this board
     */
    private Space[][] board = new Space[8][8];

    /**
     * int representing all redPlayer Pieces
     */
    private LinkedList<Piece> redPieces;

    /**
     * int representing all redPlayer Pieces
     */
    private LinkedList<Piece> whitePieces;


    /**
     * Create a new board with pieces in the default starting configuration
     */
    public Board() {
        redPieces = new LinkedList<>();
        whitePieces = new LinkedList<>();

        BoardController.initBoard(board, redPieces, whitePieces);
    }

    /**
     * Get the BoardView representation of this Board
     * @return BoardView containing the pieces from this board
     */
    public BoardView getBoardView(PieceColor color) {
        return new BoardView(board, color);
    }

    /**
     * getMatrix()
     * @return board
     */
    public Space[][] getMatrix(){
        return board;
    }

    /**
     * Given a position it returns the peice that is at that location
     * @param position
     * @return Piece
     */
    public Piece valueAt(Position position){
        return board[position.getRow()][position.getCell()].getPiece();
    }

    public void removePiece(Piece piece){
        if(piece.getColor() == PieceColor.RED)
            redPieces.remove(piece);
        else
            whitePieces.remove(piece);
    }

    public void clearSpace(Position position){
        board[position.getRow()][position.getCell()] = new Space(position.getCell(), null);
    }

    public int getNumRedPieces() {
        return redPieces.size();
    }

    public int getNumWhitePieces() {
        return whitePieces.size();
    }
}

package com.webcheckers.model;

import com.sun.org.apache.regexp.internal.RE;
import com.webcheckers.gameview.BoardView;

/**
 * The server-side representation of a Checkers board
 */
public class Board {

    /**
     * The 2D array of pieces in this board
     */
    private Piece[][] board = new Piece[8][8];

    /**
     * Create a new board with pieces in the default starting configuration
     */
    public Board() {
        //white at the top
        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[2][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[6][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
            } else {
                board[1][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[5][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
                board[7][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
            }
        }
    }

    /**
     * Get the BoardView representation of this Board
     * @param perspectiveColor which player's perspective the view should be from
     * @return BoardView containing the pieces from this board
     */
    public BoardView getBoardView(PieceColor perspectiveColor) {
        if (perspectiveColor == PieceColor.WHITE) {
            return new BoardView(rotate180());
        } else {
            return new BoardView(board);
        }
    }

    /**
     * Get a 180-degree rotation of the board, for the other player's perspective
     * @return Piece array rotated 180 degrees
     */
    private Piece[][] rotate180() {
        Piece[][] rotated = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                rotated[7 - row][7 - col] = board[row][col];
            }
        }
        return rotated;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof  Board){
            Board otherBoard = (Board)other;
            if (getBoardView(PieceColor.WHITE).equals(otherBoard.getBoardView(PieceColor.WHITE))){
                return getBoardView(PieceColor.RED).equals(otherBoard.getBoardView(PieceColor.WHITE));
            }
        }
        return false;
    }
}

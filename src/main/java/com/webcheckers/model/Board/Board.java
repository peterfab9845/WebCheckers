package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;

/**
 * Created by Curtis Veronesi on 3/2/2018.
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
     * @return BoardView containing the pieces from this board
     */
    public BoardView getBoardView() {
        return new BoardView(board);
    }

}

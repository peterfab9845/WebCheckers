package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;

/**
 * Created by Curtis Veronesi on 3/2/2018.
 */
public class Board {

    private Piece[][] board = new Piece[8][8];

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

    public BoardView getBoardView(PieceColor perspectiveColor) {
        if (perspectiveColor == PieceColor.WHITE) {
            return new BoardView(rotate180());
        } else {
            return new BoardView(board);
        }
    }

    private Piece[][] rotate180() {
        Piece[][] rotated = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                rotated[7 - row][7 - col] = board[row][col];
            }
        }
        return rotated;
    }
}

package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;
import com.webcheckers.gameview.Piece;
import com.webcheckers.gameview.PieceColor;
import com.webcheckers.gameview.PieceType;

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

    public BoardView getBoardView() {
        return new BoardView(board);
    }
}

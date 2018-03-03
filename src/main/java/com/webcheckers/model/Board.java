package com.webcheckers.model;

import com.webcheckers.gameview.PieceColor;

/**
 * Created by Curtis Veronesi on 3/2/2018.
 */
public class Board {

    private PieceColor[][] board = new PieceColor[8][8];

    public Board() {
        //white at the top
        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col] = PieceColor.WHITE;
                board[2][col] = PieceColor.WHITE;
                board[6][col] = PieceColor.RED;
            } else {
                board[1][col] = PieceColor.WHITE;
                board[5][col] = PieceColor.RED;
                board[7][col] = PieceColor.RED;
            }
        }
    }
}

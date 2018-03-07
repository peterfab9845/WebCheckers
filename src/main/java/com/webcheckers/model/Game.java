package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;

/**
 * Created by Curtis Veronesi on 3/2/2018.
 */
public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Board board;

    public Game(Player red, Player white) {
        redPlayer = red;
        whitePlayer = white;
        board = new Board();
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }
    
    public PieceColor getPlayerColor(Player player) {
        if (player == redPlayer) {
            return PieceColor.RED;
        } else {
            return PieceColor.WHITE;
        }
    }

    public BoardView getBoardView(Player player) {
        if (player.equals(whitePlayer)) {
            return board.getBoardView(PieceColor.WHITE);
        } else {
            return board.getBoardView(PieceColor.RED);
        }
    }

    public PieceColor getActiveColor() {
        return PieceColor.RED;
    }
    
    public Board getBoard() {
        return board;
    }
}

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

    public BoardView getBoardView() {
        return board.getBoardView();
    }

}

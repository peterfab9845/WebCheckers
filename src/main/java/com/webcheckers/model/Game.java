package com.webcheckers.model;

import com.google.gson.Gson;

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

    //TODO rewrite this
    public String getBoardView() {
        Gson gson = new Gson();
        return gson.toJson(board);
    }

}

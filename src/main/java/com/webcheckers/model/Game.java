package com.webcheckers.model;

import com.google.gson.Gson;

/**
 * Created by Curtis Veronesi on 3/2/2018.
 */
public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Board board;

    public Game(Player One, Player Two){
        playerOne = One;
        playerTwo = Two;
        board = new Board();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    //TODO rewrite this
    public String getBoardView(){
        Gson gson = new Gson();
        return gson.toJson(board);
    }

}

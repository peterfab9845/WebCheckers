package com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;

import java.util.HashMap;

public class GameManager {

    private HashMap<String, Game> games;

    public GameManager(){
        games = new HashMap<>();
    }

    /**
     * Removes game from list of games, and assigns winners
     * @param player
     */
    public void removeGame(Player player){
        Game game = getGame(player);
        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();

        if( player == whitePlayer ){
            redPlayer.justWon();
            whitePlayer.justLost();
        }
        else{
            redPlayer.justLost();
            whitePlayer.justWon();
        }

        games.remove(game.getWhitePlayer());
        games.remove(game.getRedPlayer());
    }

    /**
     * puts two players in a game together
     * @param player
     * @param challenging
     */
    public void challenge(Player player, Player challenging){
        player.setInGame();
        challenging.setInGame();
        Game game = new Game(player, challenging);
        player.setTeamColor(PieceColor.RED);
        challenging.setTeamColor(PieceColor.WHITE);
        addGame(player, game);
        addGame(challenging, game);
    }

    /**
     * Adds a game to the game library
     * @param player
     * @param game
     */
    public void addGame(Player player, Game game){
        games.put(player.getName(), game);
    }



    /**
     * Given a player returns the game that they are in
     * @param player
     * @return
     */
    public Game getGame(Player player){
        return games.get(player.getName());
    }
}

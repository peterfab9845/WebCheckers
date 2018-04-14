package com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import spark.Session;

import java.util.Iterator;

/**
 * Map of each player's session ID to its respective Player object, along with their game data.
 */
public class PlayerLobby {


    private PlayerManager playerManager;
    private GameManager gameManager;



    /**
    * Constructor
    */
    public PlayerLobby(){
        gameManager = new GameManager();
        playerManager = new PlayerManager();
    }

    public void addPlayer(Player player) {
        playerManager.addPlayer(player);
    }

    /**
     * Removes player from lobby
     *  @param player
     */
    public void removePlayer(Player player){
        playerManager.removePlayer(player);
        if(player.isInLobby())
            return;
        removeGame(player);
    }

    /**
     * given a session it returns the player in lobby with that session
     * @param session
     * @return Players
     */
    public Player getPlayer(Session session){
        return playerManager.getPlayer(session);
    }

    /**
     * given a String it returns the player in lobby with that username
     * @param name
     * @return Player
     */
    public Player getPlayer(String name){
        return playerManager.getPlayer(name);
    }


    /**
    * returns if username is taken
    * @param username
    * @return boolean of username taken
    */
    public boolean playerExists(String username) {
    return playerManager.playerExists(username);
    }

    /**
    * Removes game from list of games, and assigns winners
    * @param player
    */
    public void removeGame(Player player){
        gameManager.removeGame(player);
    }

    /**
    * Returns a iterator of players in the lobby
    * @return iterator of player
    */
    public Iterator<Player> getPlayersInLobby(){
    return playerManager.getPlayersInLobby();
    }

    /**
     * Returns a iterator of players in the lobby except the requested player
     * @param session
     * @return iterator of player
     */
    public Iterator<Player> getPlayersInLobbyExcept(Session session){
        return playerManager.getPlayersInLobbyExcept(session);
    }

    /**
     * Returns a iterator of players in the lobby except the requested player
     * @param session
     * @return iterator of player
     */
    public Iterator<Player> getPlayersInGameExcept(Session session){
        return playerManager.getPlayersInGameExcept(session);
    }

    /**
    * gets number of players in the lobby
    * @return integer of players in loby
    */
    public int playersInLobby(){
    return playerManager.countInLobby();
    }


    /**
    * puts two players in a game together
    * @param player
    * @param challenging
    */
    public void challenge(Player player, Player challenging){
        gameManager.challenge(player, challenging);
    }

    /**
    * Adds a game to the game library
    * @param player
    * @param game
    */
    public void addGame(Player player, Game game){
        gameManager.addGame(player, game);
    }

    /**
    * Given a player returns the game that they are in
    * @param player
    * @return
    */
    public Game getGame(Player player){
        return gameManager.getGame(player);
    }
}
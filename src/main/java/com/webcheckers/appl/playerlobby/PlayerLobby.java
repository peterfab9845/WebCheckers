package com.webcheckers.appl.playerlobby;

import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.states.PieceColor;
import spark.Session;

import java.util.Iterator;


/**
 * Map of each player's session ID to its respective Player object, along with their game data.
 */
public class PlayerLobby {


    private PlayerManager playerManager;
    private GameManager gameManager;

    private static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";

    /**
    * Constructor
    */
    public PlayerLobby(){
        gameManager = new GameManager();
        playerManager = new PlayerManager();
    }

    public boolean validUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public void addPlayer(Player player, Session session) {
        playerManager.addPlayer(player, session);
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
    public void removeGame(PlayerEntity player){
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
    return playerManager.playersInLobby();
    }


    /**
    * puts two players in a game together
    * @param player
    * @param challenging
    */
    public void challenge(PlayerEntity player, PlayerEntity challenging){
        gameManager.challenge(player, challenging);
    }

    /**
    * Adds a game to the game library
    * @param player
    * @param game
    */
    public void addGame(PlayerEntity player, Game game){
        gameManager.addGame(player, game);
    }

    /**
    * Given a player returns the game that they are in
    * @param player
    * @return
    */
    public Game getGame(PlayerEntity player){
        return gameManager.getGame(player);
    }

    public Game challengeAI(AI ai, PlayerEntity player){
        player.setInGame();
        ai.setInGame();
        Game game = new Game( player, ai );
        player.setTeamColor(PieceColor.RED);
        ai.setTeamColor(PieceColor.WHITE);
        gameManager.addGame(player, game);
        gameManager.addGame(ai, game);
        return game;
    }

    public void addSpectator(PlayerEntity player, Game game){
        player.setInGame();
        gameManager.addGame(player, game);
    }

}
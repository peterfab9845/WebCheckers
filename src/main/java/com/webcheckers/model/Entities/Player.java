package com.webcheckers.model.Entities;

import com.webcheckers.model.gamesaves.GameLog;
import spark.Session;

import java.util.Iterator;
import java.util.LinkedList;

public class Player extends PlayerEntity {

    /**
     * Current Players session
     */
    private Session session;

    /**
    * Holds all previous games played
    */
    private LinkedList<GameLog> previousGames;

    /**
    * Constructor
    * @param name
    * @param session
    */
    public Player(String name, Session session){
        super(name);
        this.session = session;
        this.previousGames = new LinkedList<>();
        sendToLobby();
    }


    /**
     * gives the session of the current player
     * @return Session
     */
    public Session getSession() {
        return session;
    }

    /**
    * Turns the list of games into an iterator
    * @return iterator of player's saved games
    */
    public Iterator getGameLogs(){
        return previousGames.iterator();
    }

    /**
     * Deletes a game save
     * @param game
     * @return
     */
    public boolean deleteGame(String game) {
        for(int i = 0; i < previousGames.size(); i++) {
            if(previousGames.get(i).toString().equals(game)) {
                this.previousGames.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
    * Add this game to the players list of games
    * @param gameLog info from the last game played
    */
    public void saveGame(GameLog gameLog){
        previousGames.add(gameLog);
    }

    /**
    * Hashes The player with their session
    * @return int
    */
    @Override
    public int hashCode(){
        return session.hashCode();
    }
}

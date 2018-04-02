package com.webcheckers.model.Entities;

import com.webcheckers.model.GameSaves.GameLog;
import com.webcheckers.model.States.PlayerStates;
import spark.Session;

import java.util.Iterator;
import java.util.LinkedList;

public class Player extends Entity {

  private PlayerStates currentState;
  private Session session;
  /**
   * Holds all previous games played
   */
  private LinkedList<GameLog> previousGames;

  // UserName For user
  private String name;

  public Player(String name, Session session){
    super();
    this.name = name;
    this.session = session;
    this.previousGames = new LinkedList<>();
    sendToLobby();
  }

  public void sendToLobby(){
    this.currentState = PlayerStates.IN_LOBBY;
  }

  public boolean isInLobby(){
    return currentState == PlayerStates.IN_LOBBY;
  }

  public Session getSession() {
    return session;
  }

  public String getName() {
    return name;
  }

  public void setUserName(String userName) {
    this.name = userName;
  }

  public void setInGame(){
    this.currentState = PlayerStates.IN_GAME;
  }


  /**
   * Add this game to the players list of games
   * @param gameLog info from the last game played
   */
  public void saveGame(GameLog gameLog){
    previousGames.add(gameLog);
  }

  /**
   * Turns the list of games into an iterator
   * @return iterator of player's saved games
   */
  public Iterator getGameLogs(){
    return previousGames.iterator();
  }

  public boolean deleteGame(String game) {
    for(int i = 0; i < previousGames.size(); i++) {
      if(previousGames.get(i).toString().equals(game)) {
        this.previousGames.remove(i);
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode(){
    return session.hashCode();
  }
}

package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PlayerStates;
import spark.Session;

public class Player extends Entity {

  private PlayerStates currentState;
  private Session session;

  // UserName For user
  private String name;

  public Player(String name, Session session){
    super();
    this.name = name;
    this.session = session;
  }

  public void sendToLobby(){
    this.currentState = PlayerStates.INLOBBY;
  }

  public boolean isInLobby(){
    return currentState == PlayerStates.INLOBBY;
  }



  public String getName() {
    return name;
  }

  public void setUserName(String userName) {
    this.name = userName;
  }

  @Override
  public int hashCode(){
    return session.hashCode();
  }
}

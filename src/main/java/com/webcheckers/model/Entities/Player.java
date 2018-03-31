package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PlayerStates;
import spark.Session;

public class Player extends Entity {

  private PlayerStates currentState;
  private Session session;

  public Player(String userName, Session session){
    super(userName);
    this.session = session;
  }

  public void sendToLobby(){
    this.currentState = PlayerStates.INLOBBY;
  }

  public boolean isInLobby(){
    return currentState == PlayerStates.INLOBBY;
  }

  @Override
  public int hashCode(){
    return Integer.parseInt(session.id());
  }
}

package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PlayerStates;

public class Player extends Entity {

  private PlayerStates currentState;

  public Player(String userName){
    super(userName);
  }

  public void sendToLobby(){
    this.currentState = PlayerStates.INLOBBY;
  }
}

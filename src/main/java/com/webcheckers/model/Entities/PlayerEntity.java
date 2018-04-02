package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PlayerStates;

public class PlayerEntity {

  /**
   * The state of the object
   */
  private PlayerStates currentState;

  /**
   *Team Color - Set when game started
   */
  private PieceColor teamColor;

  /**
   * UserName For user
   */
  private String name;

  public PlayerEntity(String name){
    this.name = name;
  }

  /**
   * Sets the state to IN_LOBBY
   */
  public void sendToLobby(){
    this.currentState = PlayerStates.IN_LOBBY;
  }

  /**
   * Sets the state to IN_GAME
   */
  public void setInGame(){
    this.currentState = PlayerStates.IN_GAME;
  }

  /**
   * Returns if state is IN_LOBBY
   * @return boolean
   */
  public boolean isInLobby(){
    return currentState == PlayerStates.IN_LOBBY;
  }


  /*
  Getters and Setters
   */
  public PieceColor getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(PieceColor teamColor) {
    this.teamColor = teamColor;
  }

  public String getName() {
    return name;
  }

}

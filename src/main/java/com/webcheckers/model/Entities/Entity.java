package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PieceColor;

public class Entity {

  // Team Color - Set when game started
  private PieceColor teamColor;

  public Entity(){ }

  /*
  Getters and Setters
   */
  public PieceColor getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(PieceColor teamColor) {
    this.teamColor = teamColor;
  }

}

package com.webcheckers.model.Entities;

import com.webcheckers.model.States.TeamColors;

public class Entity {

  // Team Color - Set when game started
  private TeamColors teamColor;

  public Entity(){ }

  /*
  Getters and Setters
   */
  public TeamColors getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(TeamColors teamColor) {
    this.teamColor = teamColor;
  }

}

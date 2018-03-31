package com.webcheckers.model.Entities;

import com.webcheckers.model.States.TeamColors;

public class Entity {

  // Team Color - Set when game started
  private TeamColors teamColor;

  // UserName For user
  private String userName;

  public Entity(String userName){
    this.userName = userName;
  }

  /*
  Getters and Setters
   */
  public TeamColors getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(TeamColors teamColor) {
    this.teamColor = teamColor;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}

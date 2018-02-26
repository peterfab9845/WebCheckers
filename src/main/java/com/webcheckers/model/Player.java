package com.webcheckers.model;

import java.util.Objects;

public class Player {
  private String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Player)) {
      return false;
    }
    else {
      return name.equals(((Player) obj).getName());
    }
  }

  public int hashCode() {
    return Objects.hash(name);
  }

}

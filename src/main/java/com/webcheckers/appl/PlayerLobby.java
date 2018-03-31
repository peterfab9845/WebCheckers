package com.webcheckers.appl;

import com.webcheckers.model.Entities.Player;

import java.util.LinkedList;
import java.util.stream.Stream;

public class PlayerLobby{

  private LinkedList<Player> players;

  public static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";


  public PlayerLobby(){
    players = new LinkedList<Player>();
  }

  public void addPlayer(Player player){
    players.add(player);
  }

  public Stream<Player> getPlayersInLobby(){
    return players.stream().filter(Player::isInLobby);
  }

  public boolean playerExists(String username) {
    return players.stream().anyMatch(player -> player.getUserName().equals(username));
  }

  public boolean validUsername(String username) {
    return username.matches(USERNAME_REGEX);
  }
}
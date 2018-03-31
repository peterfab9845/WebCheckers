package com.webcheckers.appl;

import com.webcheckers.model.Entities.Player;
import spark.Session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class PlayerLobby{

  private HashMap<String, Player> players;

  public static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";


  public PlayerLobby(){
    players = new HashMap<String, Player>();
  }

  public void addPlayer(Player player, Session session){
    players.put(session.id(), player);
  }

  public Stream<Player> getPlayersInLobby(){
    return listOfPlayers().stream().filter(Player::isInLobby);
  }

  public boolean playerExists(String username) {
    return listOfPlayers().stream().anyMatch(player -> player.getName().equals(username));
  }

  public boolean validUsername(String username) {
    return username.matches(USERNAME_REGEX);
  }

  public LinkedList<Player> listOfPlayers(){
    LinkedList<Player> playerList = new LinkedList<>();
    Player player;
    for(Map.Entry<String, Player> entry : players.entrySet()) {
      player = entry.getValue();
      playerList.add(player);
    }
    return playerList;
  }

  public Player getPlayer(Session session){
    return players.get(session.id());
  }
}
package main.java.com.webcheckers.appl;

import java.util.LinkedList;

public class PlayerLobby{

  private static LinkedList<Player> players;

  public PlayerLobby(){
    players = new LinkedList<Player>();
  }

  public void addPlayer(Player player){
    players.add(player);
  }
}
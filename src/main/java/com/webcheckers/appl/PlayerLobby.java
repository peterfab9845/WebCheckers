package com.webcheckers.appl;

import com.webcheckers.model.Entities.Game;
import com.webcheckers.model.Entities.Player;
import spark.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class PlayerLobby{

  private HashMap<String, Player> players;
  private LinkedList<Game> games;

  private static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";

  public PlayerLobby(){
    players = new HashMap<>();
    games = new LinkedList<>();
  }

  public void addPlayer(Player player, Session session){
    players.put(session.id(), player);
  }

  /**
   * Returns a iterator of players in the lobby
   * @return iterator of player
   */
  public Iterator<Player> getPlayersInLobby(){
    Stream<Player> playerStream = listOfPlayers().stream();
    //filter players out of lobby
    playerStream = playerStream.filter(Player::isInLobby);
    return playerStream.iterator();
  }

  /**
   * Returns a iterator of players in the lobby except the requested player
   * @param session
   * @return iterator of player
   */
  public Iterator<Player> getPlayersInLobbyExcept(Session session){
    String sessionid = session.id();
    Stream<Player> playerStream = listOfPlayers().stream();
    //filter players out of lobby
    playerStream = playerStream.filter(Player::isInLobby);
    //filter out the exepted player
    playerStream = playerStream.filter(p -> !p.getSession().id().equals(sessionid));
    return playerStream.iterator();
  }

  /**
   * gets number of players in the lobby
   * @return integer of players in loby
   */
  public int playersInLobby(){
    return (int) listOfPlayers().stream().filter(Player::isInLobby).count();
  }

  /**
   * returns if username is taken
   * @param username
   * @return boolean of username taken
   */
  public boolean playerExists(String username) {
    return listOfPlayers().stream().anyMatch(player -> player.getName().equals(username));
  }

  public boolean validUsername(String username) {
    return username.matches(USERNAME_REGEX);
  }

  private LinkedList<Player> listOfPlayers(){
    LinkedList<Player> playerList = new LinkedList<>();
    Player player;
    for(Map.Entry<String, Player> entry : players.entrySet()) {
      player = entry.getValue();
      playerList.add(player);
    }
    return playerList;
  }

  public Iterator<Player> iterator(){
    return listOfPlayers().iterator();
  }

  public Player getPlayer(Session session){
    return players.get(session.id());
  }

  public Player getPlayer(String name){
    LinkedList<Player> playerLinkedList = listOfPlayers();
    String tempName;
    for (Player aPlayerLinkedList : playerLinkedList) {
      tempName = aPlayerLinkedList.getName();
      if (tempName.equals(name)) {
        return aPlayerLinkedList;
      }
    }
    return null;
  }

  public void Challenge(Player player, Player challenging){
    player.setInGame();
    challenging.setInGame();
    Game game = new Game(player, player);
    games.add(game);
  }
}
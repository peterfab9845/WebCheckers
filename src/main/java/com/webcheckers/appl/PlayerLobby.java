package com.webcheckers.appl;

import com.webcheckers.model.Entities.Game;
import com.webcheckers.model.Entities.Player;
import com.webcheckers.model.States.PieceColor;
import spark.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Map of each player's session ID to its respective Player object, along with their game data.
 * todo: see if its worth while to split playerlobby into player and game lobby
 * todo: get opponent
 */
public class PlayerLobby {

  /**
   * The Map to store the players in.
   */
  private HashMap<String, Player> players;

  /**
   * The Map to store the games in.
   */
  private HashMap<String, Game> games;

  private static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";

  /**
   * Constructor
   */
  public PlayerLobby(){
    players = new HashMap<>();
    games = new HashMap<>();
  }


  /**
   * Add a player to the lobby.
   * @param player the player to add
   * @param session that player's session
   * @return true if that player is not already in the lobby
   */
  public boolean addPlayer(Player player, Session session) {
    if (players.containsValue(player)) {
      return false;
    }
    players.put(session.id(), player);
    return true;
  }

  /**
   * Removes player from lobby
   * @param player
   * @param session
   */
  public void removePlayer(Player player, Session session){
    players.remove(session.id());
    if(player.isInLobby())
      return;
    removeGame(player);
  }

  /**
   * Removes game from list of games, and assigns winners
   * @param player
   */
  public void removeGame(Player player){
      Game game = getGame(player);
      Player redPlayer = game.getRedPlayer();
      Player whitePlayer = game.getWhitePlayer();

      if( player == whitePlayer ){
          redPlayer.justWon();
          whitePlayer.justLoss();
      }
      else{
          redPlayer.justLoss();
          whitePlayer.justWon();
      }

      games.remove(game.getWhitePlayer());
      games.remove(game.getRedPlayer());
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

  /**
   * given a session it returns the player in lobby with that session
   * @param session
   * @return Players
   */
  public Player getPlayer(Session session){
    return players.get(session.id());
  }

  /**
   * given a String it returns the player in lobby with that username
   * @param name
   * @return Player
   */
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

  /**
   * puts two players in a game together
   * @param player
   * @param challenging
   */
  public void challenge(Player player, Player challenging){
    //todo: check if challenging is in game
    player.setInGame();
    challenging.setInGame();
    Game game = new Game(player, challenging);
    player.setTeamColor(PieceColor.RED);
    challenging.setTeamColor(PieceColor.WHITE);
    addGame(player, game);
    addGame(challenging, game);
  }

  /**
   * Adds a game to the game library
   * @param player
   * @param game
   */
  public void addGame(Player player, Game game){
    games.put(player.getName(), game);
  }

  /**
   * Given a player returns the game that they are in
   * @param player
   * @return
   */
  public Game getGame(Player player){
      return games.get(player.getName());
  }
}
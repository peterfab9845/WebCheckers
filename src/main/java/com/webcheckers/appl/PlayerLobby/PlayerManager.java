package com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.entities.Player;
import spark.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class PlayerManager {

    /**
     * The Map to store the players in.
     */
    private HashMap<String, Player> players;


    public PlayerManager() {
        players = new HashMap<>();
    }

    /**
     * Add a player to the lobby.
     * @param player the player to add
     * @return true if that player is not already in the lobby
     */
    public boolean addPlayer(Player player) {
        if (players.containsValue(player)) {
            return false;
        }
        Session playerSession = player.getSession();
        players.put(playerSession.id(), player);
        return true;
    }

    /**
     * returns if username is taken
     * @param username
     * @return boolean of username taken
     */
    public boolean playerExists(String username) {
        return listOfPlayers().stream().anyMatch(player -> player.getName().equals(username));
    }

    /**
     * Removes player from lobby
     * @param player
     */
    public void removePlayer(Player player) {
        Session playerSession = player.getSession();
        players.remove(playerSession.id());
    }

    /**
     * given a String it returns the player in lobby with that username
     * @param name
     * @return Player
     */
    public Player getPlayer(String name) {
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
     * given a session it returns the player in lobby with that session
     * @param session
     * @return Players
     */
    public Player getPlayer(Session session){
        return players.get(session.id());
    }
    /**
     * Returns a iterator of players in the lobby
     * @return iterator of player
     */
    public Iterator<Player> getPlayersInLobby() {
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
    public Iterator<Player> getPlayersInLobbyExcept(Session session) {
        return getPlayersExcept(session, true);
    }

    /**
     * Returns a iterator of players in the lobby except the requested player
     * @param session
     * @return iterator of player
     */
    public Iterator<Player> getPlayersInGameExcept(Session session) {
        return getPlayersExcept(session, false);
    }

    public Iterator<Player> getPlayersExcept(Session session, boolean inLobby) {
        String sessionid = session.id();
        Stream<Player> playerStream = listOfPlayers().stream();
        //filter players out of lobby
        if( inLobby )
            playerStream = playerStream.filter(Player::isInLobby);
        else
            playerStream = playerStream.filter(Player::isInGame);
        //filter out the excepted player
        playerStream = playerStream.filter(p -> !p.getSession().id().equals(sessionid));
        return playerStream.iterator();
    }


    /**
     * Gets the number of players in the lobby
     * @return count of players in lobby
     */
    public int playersInLobby() {
        return (int) listOfPlayers().stream().filter(Player::isInLobby).count();
    }

    private LinkedList<Player> listOfPlayers() {
        LinkedList<Player> playerList = new LinkedList<>();
        Player player;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            player = entry.getValue();
            playerList.add(player);
        }
        return playerList;
    }


}

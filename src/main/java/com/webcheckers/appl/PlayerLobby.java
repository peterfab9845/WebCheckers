package com.webcheckers.appl;

import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import spark.Session;

/**
 * Map of each player's session ID to its respective Player object.
 */
public class PlayerLobby {

    /**
     * The Map to store the players in.
     */
    private static HashMap<String, Player> allPlayers;

    /**
     * Initialize the map.
     */
    public static void init() {
        allPlayers = new HashMap<>();
    }

    /**
     * Add a player to the lobby.
     * @param player the player to add
     * @param session that player's session
     * @return true if that player is not already in the lobby
     */
    public static boolean addPlayer(Player player, Session session) {
        if (allPlayers.containsValue(player)) {
            return false;
        }

        allPlayers.put(session.id(), player);
        return true;
    }

    /**
     * Get a player from the map and remove them.
     * @return a player from the map
     */
    public static Player getNextPlayer() {
        if (allPlayers.isEmpty()) {
            return null;
        }
        Iterator<String> sessionIterator = allPlayers.keySet().iterator();
        return allPlayers.remove(sessionIterator.next());
    }

    /**
     * Check whether a session is in the map.
     * @param session the session to check
     * @return true if that session is already a key in the map
     */
    public static boolean sessionExists(Session session) {
        return allPlayers.containsKey(session.id());
    }

    /**
     * Get a player based on their session ID.
     * @param session The Session of the player to get
     * @return the player whose session was given
     */
    public static Player getPlayer(Session session) {
        return allPlayers.get(session.id());
    }

    /**
     * Check whether a player with the given username is in the map.
     * @param playerName the player username to check
     * @return true if the username is in the map
     */
    public static boolean playerExists(String playerName) {
        List<String> playerList = getPlayerList();
        return playerList.contains(playerName);
    }

    /**
     * Get a Player object based on a username.
     * @param playerName the username to get the Player for
     * @return the player with that username, if any; otherwise null
     */
    public static Player getPlayerByName(String playerName) {
        for (Player p : allPlayers.values()) {
            if (p.getName().equals(playerName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Create a list of the usernames of all players in the map.
     * @return a List of each player name from the map
     */
    public static List<String> getPlayerList() {
        List<String> playerNames = new ArrayList<>();
        for (Player p : allPlayers.values()) {
            playerNames.add(p.getName());
        }
        return playerNames;
    }

    /**
     * Create a list of the usernames of all players in the map,
     * except the one with the given username.
     * @param name the username to exclude
     * @return a List of each username from the map, except the given name
     */
    public static List<String> getPlayerListExcept(String name) {
        List<String> playerNames = getPlayerList();
        playerNames.remove(name);
        return playerNames;
    }

    /**
     * Get a count of players in the map.
     * @return the number of entries in the map
     */
    public static int getPlayerCount() {
        return allPlayers.size();
    }
}

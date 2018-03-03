package com.webcheckers.appl;

import com.webcheckers.model.Player;
import java.util.Iterator;
import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerLobby {

    private static HashMap<String, Player> allPlayers;

    public static void init() {
        allPlayers = new HashMap<>();
    }

    public static boolean addPlayer(Player player, Session session) {
        if (allPlayers.containsValue(player)) {
            return false;
        }

        allPlayers.put(session.id(), player);
        return true;
    }

    public static Player getNextPlayer() {
        Iterator<String> sessionIterator = allPlayers.keySet().iterator();
        return allPlayers.remove(sessionIterator.next());
    }

    public static Player getPlayer(Session session) {
        return allPlayers.get(session.id());
    }

    public static boolean sessionExists(Session session) {
        return allPlayers.containsKey(session.id());
    }

    public static int getPlayerCount() {
        return allPlayers.size();
    }

    public static List<String> getPlayerList() {
        List<String> playerNames = new ArrayList<>();
        for (Player p : allPlayers.values()) {
            playerNames.add(p.getName());
        }
        return playerNames;
    }

    public static List<String> getPlayerListExcept(String name) {
        List<String> playerNames = getPlayerList();
        playerNames.remove(name);
        return playerNames;
    }

    public static boolean playerExists(String playerName) {
        List<String> playerList = getPlayerList();
        return playerList.contains(playerName);
    }

    public static Player getPlayerByName(String playerName) {
        for (Player p : allPlayers.values()) {
            if (p.getName().equals(playerName)) {
                return p;
            }
        }
        return null;
    }
}

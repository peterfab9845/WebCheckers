package com.webcheckers.appl;

import com.webcheckers.model.Player;
import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlayerLobby {

    private static Queue<Player> allPlayers;
    private static HashMap<String, Player> allPlayersHashed;

    public static void init() {
        allPlayers = new LinkedList<>();
        allPlayersHashed = new HashMap<>();
    }

    public static boolean addPlayer(Player player, Session session) {
        if (allPlayersHashed.containsValue(player))
            return false;

        allPlayers.add(player);
        allPlayersHashed.put(session.id(), player);

        return true;
    }

    public static Player getNextPlayer() {
        return allPlayers.remove();
    }

    public static Player getPlayer(Session session){
        return allPlayersHashed.get(session.id());
    }

    public static boolean sessionExists(Session session){
        return allPlayersHashed.containsKey(session.id());
    }

    public static int getPlayerCount(){
        return allPlayersHashed.size();
    }
    
    public static List<String> getPlayerList() {
        List<String> playerNames = new ArrayList<>();
        for (Player p : allPlayersHashed.values()) {
            playerNames.add(p.getName());
        }
        return playerNames;
    }
}

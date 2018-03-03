package com.webcheckers.appl;

import com.webcheckers.model.Player;
import spark.Session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerLobby {

    private static Queue<Player> allPlayers;
    private static HashMap<String, Player> allPlayersHashed;
    private static int playerCount;

    public static void init() {
        allPlayers = new LinkedList<>();
        allPlayersHashed = new HashMap();
        playerCount = 0;
    }

    public static boolean addPlayer(Player player, Session session) {
        if (allPlayersHashed.containsValue(player))
            return false;

        allPlayers.add(player);
        allPlayersHashed.put(session.id(), player);
        playerCount++;
        
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
}

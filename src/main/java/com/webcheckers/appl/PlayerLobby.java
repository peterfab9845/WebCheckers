package com.webcheckers.appl;

import com.webcheckers.model.Player;
import spark.Session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerLobby {

    private static Queue<Player> allPlayers;
    private static HashMap<String, Player> allPlayersHashed;

    public static void init() {
        allPlayers = new LinkedList<>();
        allPlayersHashed = new HashMap();
    }

    public static void addPlayer(Player player, Session session) {
        boolean add = allPlayers.add(player);
        allPlayersHashed.put(session.id(), player);
        if (add) {
            System.out.println("SERVER:" + player.getName() + " has signed in.");
        }
    }

    public static Player getNextPlayer() {
        return allPlayers.remove();
    }

    public static Player getPlayer(Session session){
        return allPlayersHashed.get(session.id());
    }
}

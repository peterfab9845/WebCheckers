package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerLobby {

    private static Queue<Player> allPlayers;

    public static void init() {
        allPlayers = new LinkedList<>();
    }

    public static void addPlayer(Player player) {
        boolean add = allPlayers.add(player);
        if (add) {
            System.out.println("SERVER:" + player.getName() + " has signed in.");
        }
    }

    public static Player getNextPlayer() {
        return allPlayers.remove();
    }
}

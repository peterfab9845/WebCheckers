package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.LinkedList;

public class PlayerLobby {

    private static LinkedList allPlayers;

    public static void init(){
        allPlayers = new LinkedList<Player>();
    }

    public static void addPlayer(Player player){
        allPlayers.add(player);
    }
}

package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.LinkedList;

public class PlayerLobby {

    LinkedList allPlayers;

    public PlayerLobby(){
        this.allPlayers = new LinkedList<Player>();
    }

    void addPlayer(Player player){
        this.allPlayers.add(player);
    }
}

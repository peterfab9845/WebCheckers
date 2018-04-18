package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.PlayerEntity;

public class RecordedAI extends AI implements ArtIntel{


    /**
     * Constructor
     *
     * @param name
     * @param enemy
     * @param playerLobby
     */
    public RecordedAI(String name, String name2, PlayerEntity enemy, PlayerLobby playerLobby) {
        super(name, enemy, playerLobby);

    }

    @Override
    public void makeDecision() {

    }

}

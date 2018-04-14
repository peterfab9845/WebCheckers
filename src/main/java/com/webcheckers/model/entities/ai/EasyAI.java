package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.PieceColor;
import com.webcheckers.ui.game.GetGameRoute;

import java.util.logging.Logger;

public class EasyAI extends AI implements ArtIntel {

    /**
     * Constructor
     *
     * @param name
     * @param playerLobby
     */
    public EasyAI(String name, PlayerEntity playerEntity, PlayerLobby playerLobby) {
        super(name, playerEntity, playerLobby);
        AI self = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    myTurn((ArtIntel) self);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(EasyAI.class.getName());

    @Override
    public void makeDecision() {
        this.game = getGame(playerLobby);
        Move move = getRandomMove();
        if(move != null)
            makeMove(move);
    }

}

package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.gamesaves.GameLog;

import java.util.Queue;

import static java.lang.Thread.*;

public class RecordedAI extends AI implements ArtIntel{


    private Queue<Move> moveQueue;
    private int name = 0;
    private String whiteName;
    private Player user;

    /**
     * Constructor
     *
     * @param name
     * @param enemy
     * @param playerLobby
     */
    public RecordedAI(Player user, String name, PlayerEntity enemy, Queue<Move> moves, PlayerLobby playerLobby) {
        super(name, enemy, playerLobby);
        this.user = user;
        moveQueue = moves;
        ArtIntel self = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    myTurn(self);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    public void makeDecision() {
        while(!moveQueue.isEmpty()){
            try { sleep((long)(user.getViewSpeed() + 100)); }
            catch (InterruptedException e) { e.printStackTrace(); }
            getGame(playerLobby);
            if(!moveQueue.isEmpty()) {
                Move move = moveQueue.remove();
                if (MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(), true, false))
                    makeMove(move);
            }
        }

    }


}
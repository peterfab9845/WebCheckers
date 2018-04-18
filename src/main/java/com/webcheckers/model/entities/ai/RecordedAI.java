package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.entities.PlayerEntity;

import java.util.Queue;

import static java.lang.Thread.*;

public class RecordedAI extends AI implements ArtIntel{


    private Queue<Move> moveQueue;
    private int name = 0;
    private String whiteName;

    /**
     * Constructor
     *
     * @param name
     * @param enemy
     * @param playerLobby
     */
    public RecordedAI(String name, String name2, PlayerEntity enemy, PlayerLobby playerLobby) {
        super(name, enemy, playerLobby);
        whiteName = name2;
    }

    @Override
    public void makeDecision() {
        while(!moveQueue.isEmpty()){
            try { sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            getGame(playerLobby);
            Move move = moveQueue.remove();
            if(MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(),true, false))
                makeMove(move);
        }
    }

    @Override
    public String getName(){
        name++;
        if(name % 2 == 0)
            super.getName();
        return this.whiteName;
    }

}

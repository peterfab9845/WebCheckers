package com.webcheckers.appl.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.entities.ai.EasyAI;
import com.webcheckers.model.entities.ai.HardAI;

import java.util.Random;

public class AITrainer extends Thread {


    private final static long SLEEP_TIME = (long)2000.0;

    private Game game[];
    private PlayerEntity user;
    private PlayerLobby playerLobby;

    public AITrainer(Game game[], PlayerEntity user, PlayerLobby playerLobby){
        this.game = game;
        this.user = user;
        this.playerLobby = playerLobby;
    }

    public void run() {
        do{
            try {
                this.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!game[0].isGameInSession()){
                AI ai = new HardAI(AIManager.getName(), user, playerLobby);
                AI ai2;
                Random random = new Random();
                if(random.nextInt(10)% 2 == 0)
                    ai2 = new EasyAI(AIManager.getName(), user, playerLobby);
                else
                    ai2 = new HardAI(AIManager.getName(), user, playerLobby);

                game[0] = playerLobby.challengeAI(ai, ai2);
                playerLobby.addSpectator(user, game[0]);
            }
        }while((AIManager.isDebugging()));
    }
}

package com.webcheckers.ui.ai;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.AIManager;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.entities.ai.EasyAI;
import com.webcheckers.model.entities.ai.HardAI;
import com.webcheckers.model.entities.ai.MediumAI;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostAIRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(PostAIRoute.class.getName());


    /**
     * Gson object for transporting data
     */
    private final Gson gson;

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /checkTurn} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostAIRoute(final Gson gson, PlayerLobby playerLobby) {

        this.playerLobby = playerLobby;

        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        //

        this.gson = gson;
        //
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    /**
     * Handle the player's check turn request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostAIRoute is invoked.");

        Player user = playerLobby.getPlayer(request.session());
        if( user == null || !user.isInLobby() ) {
            response.redirect("/");
            throw halt(100);
        }

        String type = request.queryParams("type");
        AI ai = new AI(AIManager.getName(), user, playerLobby);
        if(type.equals("easy") )
            ai = new EasyAI(AIManager.getName(), user, playerLobby);
        if( type.equals("medium") )
            ai = new MediumAI(AIManager.getName(), user, playerLobby);
        if( type.equals("hard") ) {
            ai = new HardAI(AIManager.getName(), user, playerLobby);
        }
        if( type.equals("train") ){
            ai = new HardAI(AIManager.getName(), user, playerLobby);
            AI ai2 = new HardAI(AIManager.getName(), user, playerLobby);

            final Game[] game = {playerLobby.challengeAI(ai, ai2)};
            playerLobby.addSpectator(user, game[0]);
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while(AIManager.isDebugging()){
                        try {
                            this.sleep((long) 1000.0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!game[0].isGameInSession()){
                            AI ai = new HardAI(AIManager.getName(), user, playerLobby);
                            AI ai2 = new HardAI(AIManager.getName(), user, playerLobby);
                            game[0] = playerLobby.challengeAI(ai, ai2);
                            playerLobby.addSpectator(user, game[0]);
                        }
                        break;
                    }
                }
            };
            thread.start();
            response.redirect("/game");
            throw halt(1002);
        }

        playerLobby.challengeAI(ai, user);

        response.redirect("/game");
        throw halt(1002);
    }

}

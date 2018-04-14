package com.webcheckers.ui.ai;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.AINaming;
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
        AI ai = new AI(AINaming.getName(), user, playerLobby);
        if(type.equals("easy") )
            ai = new EasyAI(AINaming.getName(), user, playerLobby);
        if( type.equals("medium") )
            ai = new MediumAI(AINaming.getName(), user, playerLobby);
        if( type.equals("hard") )
            ai = new HardAI(AINaming.getName(), user, playerLobby);
        if( type.equals("train") ){
            ai = new HardAI(AINaming.getName(), user, playerLobby);
            AI ai2 = new HardAI(AINaming.getName(), user, playerLobby);
            Game game = playerLobby.challengeAI(ai, ai2);
            playerLobby.addSpectator(user, game);
            response.redirect("/game");
            throw halt(1002);
        }

        playerLobby.challengeAI(ai, user);

        response.redirect("/game");
        throw halt(1002);
    }

}

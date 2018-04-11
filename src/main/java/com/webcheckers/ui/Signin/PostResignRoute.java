package com.webcheckers.ui.Signin;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.appl.PlayerLobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.States.MessageType;
import com.webcheckers.ui.Game.GetGameRoute;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostResignRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());


    /**
     * Gson object for transporting data
     */
    private final Gson gson;

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Constructor
     * @param gson
     * @param playerLobby
     */
    public PostResignRoute(final Gson gson, PlayerLobby playerLobby){
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        this.playerLobby = playerLobby;
        //
        this.gson = gson;
        //
        LOG.config("PostResignRoute is initialized.");
    }

    /**
     * Handles the resignation request
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignRoute is invoked.");

        Player user = playerLobby.getPlayer(request.session());
        playerLobby.removeGame(user);

        return gson.toJson(new Message("Success", MessageType.info));
    }
}
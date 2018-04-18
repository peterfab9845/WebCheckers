package com.webcheckers.ui.game;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostEndRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;


    public PostEndRoute(final Gson gson, PlayerLobby playerLobby) {

        // validation
        Objects.requireNonNull(gson, "gson must not be null");

        this.playerLobby = playerLobby;


        LOG.config("PostEndRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostEndRoute is invoked.");

        Player user = playerLobby.getPlayer(request.session());
        user.sendToLobby();

        response.redirect("/");
        halt(501);
        return null;
    }
}
package com.webcheckers.ui.Signin;

import com.google.gson.Gson;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Player;
import com.webcheckers.model.States.MessageType;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());


    private final Gson gson;
    private PlayerLobby playerLobby;

    public PostResignRoute(final Gson gson, PlayerLobby playerLobby){
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        this.playerLobby = playerLobby;
        //
        this.gson = gson;
        //
        LOG.config("PostResignRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignRoute is invoked.");

        Player user = playerLobby.getPlayer(request.session());
        playerLobby.removeGame(user);

        return gson.toJson(new Message("Success", MessageType.info));
    }
}
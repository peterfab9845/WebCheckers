package com.webcheckers.ui.Movement;

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

public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

    private final Gson gson;
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /backupMove} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostBackupMoveRoute(final Gson gson, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        //
        this.gson = gson;
        //
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     * Handle the player's backup move request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");

        Player currentPlayer = playerLobby.getPlayer(request.session());
        Message responseMessage = new Message("", MessageType.info);
        return gson.toJson(responseMessage);
    }
}

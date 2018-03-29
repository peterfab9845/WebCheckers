package com.webcheckers.ui;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to check if the opponent has finished a turn.
 *
 * @author Adam Heeter
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /checkTurn} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostCheckTurnRoute(final Gson gson) {
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
        LOG.finer("PostCheckTurnRoute is invoked.");
        
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        Message responseMessage = checkTurn(currentPlayer);
        return gson.toJson(responseMessage);
    }
    
    private Message checkTurn(Player currentPlayer) {
        if (currentPlayer.isTurn()) {
            return new Message("true", MessageType.info);
        } else {
            return new Message("false", MessageType.info);
        }
    }
}
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
 * The UI Controller to submit the player's queue of moves for this turn.
 *
 * @author Adam Heeter
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /submitTurn} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostSubmitTurnRoute(final Gson gson) {
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        //
        this.gson = gson;
        //
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    /**
     * Handle the player's submit turn request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSubmitTurnRoute is invoked.");
        
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        Message responseMessage = submitTurn(currentPlayer);
        return gson.toJson(responseMessage);
    }
    
    private Message submitTurn(Player currentPlayer) {
        if (currentPlayer.getLastMove() == null) {
            return new Message("You have not made any moves this turn.", MessageType.error);
        }
        
        if (currentPlayer.makeMoves()) {
            currentPlayer.endTurn();
            return new Message("Turn submitted.", MessageType.info);
        } else {
            return new Message("The board was not updated.", MessageType.error);
        }
    }
}
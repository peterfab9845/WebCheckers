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
 * The UI Controller to remove a move from the player's queue of moves for this turn.
 *
 * @author Adam Heeter
 */
public class PostBackupMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /backupMove} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostBackupMoveRoute(final Gson gson) {
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
        
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        Message responseMessage = backupMove(currentPlayer);
        return gson.toJson(responseMessage);
    }
    
    private Message backupMove(Player currentPlayer) {
        if (currentPlayer.getLastMove() != null) {
            currentPlayer.removeLastMove();
            return new Message("Backed up one move.", MessageType.info);
        } else {
            return new Message("You have not made any moves this turn.", MessageType.error);
        }
    }
}
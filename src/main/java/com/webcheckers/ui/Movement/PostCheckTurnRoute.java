package com.webcheckers.ui.Movement;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Game;
import com.webcheckers.model.Entities.Player;

import com.webcheckers.model.States.MessageType;
import com.webcheckers.ui.Game.GetGameRoute;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to check if the opponent has finished a turn.
 */
public class PostCheckTurnRoute implements Route {

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
     * Create the Spark Route (UI controller) for the {@code POST /checkTurn} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostCheckTurnRoute(final Gson gson, PlayerLobby playerLobby) {

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
        LOG.finer("PostCheckTurnRoute is invoked.");

        Player currentPlayer = playerLobby.getPlayer(request.session());
        Game game = playerLobby.getGame(currentPlayer);
        Message responseMessage = checkTurn(currentPlayer, game);
        return gson.toJson(responseMessage);
    }

    private Message checkTurn(Player currentPlayer, Game game) {
        if (currentPlayer.getTeamColor() == game.getActiveColor()) {
            return new Message("true", MessageType.info);
        } else {
            return new Message("false", MessageType.info);
        }
    }
}
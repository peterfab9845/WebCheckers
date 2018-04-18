package com.webcheckers.ui.movement;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.states.MessageType;
import com.webcheckers.ui.game.GetGameRoute;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {

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
        Game game = playerLobby.getGame(currentPlayer);
        game.deQueueMove();
        Message responseMessage = new Message("", MessageType.info);
        return gson.toJson(responseMessage);
    }

}

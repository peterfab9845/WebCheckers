package com.webcheckers.ui.saves;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.gamesaves.GameLog;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostSaveRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(PostSaveRoute.class.getName());


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
    public PostSaveRoute(final Gson gson, PlayerLobby playerLobby) {

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
        GameLog gameLog = user.getLastGame();
        user.saveGame(gameLog);
        user.sendToLobby();

        response.redirect("/saves");
        throw halt(1002);
    }

}

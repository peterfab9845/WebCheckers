package com.webcheckers.ui.saves;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.entities.ai.RecordedAI;
import com.webcheckers.model.gamesaves.GameLog;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetWatchRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(PostSaveRoute.class.getName());


    /**
     * Template engine for desplaying things to users
     */
    private final TemplateEngine templateEngine;

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /checkTurn} HTTP request.
     *
     */
    public GetWatchRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {

        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        //
        LOG.config("GetWatchRoute is initialized.");
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
        LOG.finer("GetWatchRoute is invoked.");

        Player user = playerLobby.getPlayer(request.session());
        String game = request.queryParams("game");
        GameLog gameLog = user.getGameLogByName(game);

        AI replayAI = new RecordedAI(user, gameLog.getRedPlayer().getName(), gameLog.getWhitePlayer(), gameLog.getMoveQueue(), playerLobby);
        AI replayAI2 = new RecordedAI(user, gameLog.getWhitePlayer().getName(), gameLog.getRedPlayer(), gameLog.getMoveQueue(), playerLobby);
        Game replay;

        if(gameLog.getRedPlayer() == user)
            replay = playerLobby.challengeAI(replayAI2,replayAI);
        else
            replay =  playerLobby.challengeAI(replayAI, replayAI2);

        playerLobby.addSpectator(user, replay);

        response.redirect("/saves");
        throw halt(1002);
    }

}
package com.webcheckers.ui.game;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.ViewMode;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetSpectatingRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /**
     * Template engine for desplaying things to users
     */
    private final TemplateEngine templateEngine;

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSpectatingRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        //
        LOG.config("GetSpectatingRoute is initialized.");
    }

    /**
     * Render the WebCheckers game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSpectatingRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();

        PlayerEntity user = playerLobby.getPlayer(request.session());

        // if the opponent name is present challenge that player
        String spectateName = request.queryParams("name");
        if (spectateName != null ) {
            PlayerEntity spectatingPlayer = playerLobby.getPlayer(spectateName);
            playerLobby.addSpectator(user, spectatingPlayer);
            response.redirect("/game");
            throw halt(1102);
        }
        else{
            response.redirect("/");
            throw halt(1101);
        }
    }

}
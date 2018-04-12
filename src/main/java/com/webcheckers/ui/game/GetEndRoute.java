package com.webcheckers.ui.game;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 */
public class GetEndRoute implements Route {

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
    public GetEndRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("GetEndRoute is initialized.");
    }

    /**
    * Render the WebCheckers end page.
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for the end page
    */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetEndRoute is invoked.");
        Player user = playerLobby.getPlayer(request.session());

        Map<String, Object> vm = new HashMap<>();


        //If Player is presently logged in show them the user lost
        if ( user == null || (!user.hasWon() && !user.hasLost()) ) {
            response.redirect("/");
            throw halt(100);
        }

        vm.put("currentPlayer", user);
        if(user.hasWon()) {
            vm.put("title", "You Won!");
            vm.put("winLoss", "won.");
        }
        else if( user.hasLost()) {
            vm.put("title", "You Lost!");
            vm.put("winLoss", "loss.");
        }


        return templateEngine.render(new ModelAndView(vm , "end.ftl"));
    }

}
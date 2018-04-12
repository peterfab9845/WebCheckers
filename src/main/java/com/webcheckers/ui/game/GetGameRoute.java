package com.webcheckers.ui.game;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.states.ViewMode;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the game page.
 *
 */
public class GetGameRoute implements Route {

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
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        //
        LOG.config("GetEndRoute is initialized.");
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
        LOG.finer("GetGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();

        Player user = playerLobby.getPlayer(request.session());
        //If no player send them to home page
        if (user == null) {
            response.redirect("/");
            throw halt(401);
        }
        if( user.hasWon() || user.hasLost() ){
            response.redirect("/end");
            throw halt(403);
        }

        // if the opponent name is present challange that player
        String opponentName = request.queryParams("opponentName");
        if (opponentName != null ) {
            Player opponent = playerLobby.getPlayer(opponentName);
            playerLobby.challenge(user, opponent);
            response.redirect("/game");
            throw halt(402);
        }

        //If the player is in the lobby send them to the home page
        if( user.isInLobby() ){
            response.redirect("/");
            throw halt(402);
        }


        Game game = playerLobby.getGame(user);

        vm.put("title", "Game!");
        vm.put("currentPlayer", user);
        vm.put("viewMode", ViewMode.PLAY);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board", game.getBoardView(user.getTeamColor()));


        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}
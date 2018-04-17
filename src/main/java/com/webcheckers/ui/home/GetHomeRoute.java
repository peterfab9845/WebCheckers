package com.webcheckers.ui.home;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.playerlobby.AIManager;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.game.GetGameRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 */
public class GetHomeRoute implements Route {

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
    public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("GetEndRoute is initialized.");
    }

    /**
    * Render the WebCheckers Home page.
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
        LOG.finer("GetEndRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        Player user = playerLobby.getPlayer(request.session());

//        if ( user == null && AIManager.isDebugging()){
//            user = new Player("admin", request.session());
//            playerLobby.addPlayer(user, request.session());
//        }
        //If Player is presently logged in show them the user lost
        if ( user != null ) {
            //If the player is not in the lobby send them to their game
            if ( !user.isInLobby() ){
                response.redirect("/game");
                throw halt(100);
            }

            vm.put("currentPlayer", user);
            vm.put("playerList", playerLobby.getPlayersInLobbyExcept(request.session()));
            vm.put("gameList", playerLobby.getPlayersInGameExcept(request.session()));

        }

        vm.put("playerCount", playerLobby.playersInLobby());

        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }

}
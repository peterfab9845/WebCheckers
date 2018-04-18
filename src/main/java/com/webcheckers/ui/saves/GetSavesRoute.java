package com.webcheckers.ui.saves;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.game.GetGameRoute;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Sign-in page.
 *
 */
public class GetSavesRoute implements Route {

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
     * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetSavesRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        //
        LOG.config("GetSavesRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign-in page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSaveRoute is invoked.");

        //If the user has entered a game to delete, then delete it
        String game = request.queryParams("game");
        if(game != null){
            Player player = playerLobby.getPlayer(request.session());
            player.deleteGame(game);
            response.redirect("/saves");
            throw halt(303);
        }

        Map<String, Object> vm = new HashMap<>();

        // Redirect the player to the game page if a game is in progress or session doesnt exist
        Player currentPlayer = playerLobby.getPlayer(request.session());
        if (currentPlayer == null ) {
            response.redirect("/");
            throw halt(304);
        }

        if(!currentPlayer.isInLobby()){
            response.redirect("/game");
            throw halt(305);
        }

        vm.put("title", "Saves");
        vm.put("currentPlayer", currentPlayer);
        vm.put("saveList", currentPlayer.getGameLogs());

        return templateEngine.render(new ModelAndView(vm, "saves.ftl"));
    }

}
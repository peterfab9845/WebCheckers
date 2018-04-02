package com.webcheckers.ui.Saves;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Player;
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

    private static final Logger LOG = Logger.getLogger(GetSavesRoute.class.getName());

    private final TemplateEngine templateEngine;
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

        String game = request.queryParams("game");
        if(game != null){
            Player player = playerLobby.getPlayer(request.session());
            player.deleteGame(game);
            response.redirect("/saves");
            throw halt(303);
        }

        Map<String, Object> vm = new HashMap<>();

        Player currentPlayer = playerLobby.getPlayer(request.session());

        // Redirect the player to the game page if a game is in progress or session doesnt exist
        if (currentPlayer == null ) {
            response.redirect("/game");
            throw halt(303);
        }

        vm.put("title", "Saves");
        vm.put("currentPlayer", currentPlayer);
        vm.put("saveList", currentPlayer.getGameLogs());

        return templateEngine.render(new ModelAndView(vm, "saves.ftl"));
    }

}
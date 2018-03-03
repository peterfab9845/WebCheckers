package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        //
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        // Redirect the player to the game page if a game is in progress
        if (currentPlayer != null && currentPlayer.isInGame()) {
            response.redirect("/game");
        }
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put("currentPlayer", currentPlayer);
        vm.put("playerCount", PlayerLobby.getPlayerCount());
        if (PlayerLobby.sessionExists(request.session())) {
            vm.put("playerList", PlayerLobby.getPlayerListExcept(currentPlayer.getName()));
        }
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

}
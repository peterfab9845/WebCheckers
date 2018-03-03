package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.gameview.ViewMode;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        //

        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        if (currentPlayer != null && !currentPlayer.isInGame()) {
            String opponentName = request.queryParams("opponentName");
            if (PlayerLobby.playerExists(opponentName)) {
                Player opponent = PlayerLobby.getPlayerByName(opponentName);
                if (!requestGame(currentPlayer, opponent)) {
                    // The player is already in a game
                    // TODO display an error message
                    response.redirect("/");
                    throw halt(400);
                }
            } else {
                // The player does not exist in the system
                // TODO display an error message
                response.redirect("/");
                throw halt(400);
            }
        } else if (currentPlayer == null) {
            response.redirect("/");
            throw halt(401);
        }

        Game game = currentPlayer.getGame();
        vm.put("title", "Checkers Game");
        vm.put("currentPlayer", currentPlayer);
        vm.put("viewMode", ViewMode.PLAY);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board", game.getBoardView(currentPlayer));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

    private boolean requestGame(Player currentPlayer, Player opponent) {
        if (opponent.isInGame()) {
            return false;
        }

        // Create the game object that will be used as the model for both players
        Game game = new Game(currentPlayer, opponent);
        currentPlayer.setGame(game);
        opponent.setGame(game);
        currentPlayer.setInGame(true);
        opponent.setInGame(true);
        return true;
    }

}
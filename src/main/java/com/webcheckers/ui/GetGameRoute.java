package com.webcheckers.ui;

import com.webcheckers.gameview.BoardView;
import com.webcheckers.gameview.PieceColor;
import com.webcheckers.gameview.ViewMode;
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
        vm.put("title", "Checkers Game");
        vm.put("currentPlayer", new Player("test playernam"));
        vm.put("viewMode", ViewMode.PLAY);
        vm.put("redPlayer", new Player("test red name"));
        vm.put("whitePlayer", new Player("test white name"));
        vm.put("activeColor", PieceColor.WHITE);
        vm.put("board", new BoardView());
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

}
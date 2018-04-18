package com.webcheckers.ui.signin;

import static spark.Spark.halt;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.states.MessageType;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.game.GetGameRoute;
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
 * The UI Controller to POST the chosen username to the server.
 *
 * @author Peter Fabinski
 */
public class PostSigninRoute implements Route {

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

    //Constants
    public static final String VIEW_NAME = "signin.ftl";
    public static final String PAGE_TITLE = "Sign-in";
    public static final String ATTR_TITLE = "title";
    public static final String ATTR_MESSAGE = "message";
    public static final String ATTR_CURRENT_PLAYER = "currentPlayer";
    public static final String REQUEST_PARAM_NAME = "name";

    public static final String MSG_MISSING_USERNAME = "You must provide a username";
    public static final String MSG_INVALID_USERNAME = "Invalid username; must be alphanumeric";
    public static final String MSG_TAKEN_USERNAME = "Username taken, please choose a different one";

    private static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";

    /**
     * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSigninRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostSigninRoute is initialized.");
    }

    /**
     * Handle the user's sign-in request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSigninRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put(ATTR_TITLE, PAGE_TITLE);

        // retrieve request parameter
        final String username = request.queryParams(REQUEST_PARAM_NAME);
        if (username == null) {
            vm.put(ATTR_MESSAGE, new Message(MSG_MISSING_USERNAME, MessageType.error));
        } else if (!username.matches(USERNAME_REGEX)) {
            vm.put(ATTR_MESSAGE, new Message(MSG_INVALID_USERNAME, MessageType.error));
        } else if (playerLobby.playerExists(username)) {
            vm.put(ATTR_MESSAGE, new Message(MSG_TAKEN_USERNAME, MessageType.error));
        } else {
            Player user = new Player(username, request.session());
            playerLobby.addPlayer(user);
            response.redirect("/");
            throw halt(200);
        }

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
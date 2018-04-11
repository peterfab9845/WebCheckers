package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.Message;
import com.webcheckers.appl.PlayerLobby;
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
 * The UI Controller to POST the chosen username to the server.
 *
 * @author Peter Fabinski
 */
public class PostSigninRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

    private final TemplateEngine templateEngine;
    
    public static final String VIEW_NAME = "signin.ftl";
    public static final String PAGE_TITLE = "Sign-in";
    public static final String REQUEST_PARAM_NAME = "name";
    public static final String ATTR_TITLE = "title";
    public static final String ATTR_CURRENT_PLAYER = "currentPlayer";
    public static final String ATTR_MESSAGE = "message";

    public static final String MSG_MISSING_USERNAME = "You must provide a username";
    public static final String MSG_INVALID_USERNAME = "Invalid username; must be alphanumeric";
    public static final String MSG_TAKEN_USERNAME = "Username taken, please choose a different one";

    public static final String USERNAME_REGEX = "[A-Za-z0-9 ]+";

    /**
     * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
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
        if (username != null) {
            boolean loginSuccess;
            if (username.matches(USERNAME_REGEX)) {
                Player currentPlayer = new Player(username);
                loginSuccess = PlayerLobby.addPlayer(currentPlayer, request.session());
                if (loginSuccess) { //everything worked
                    vm.put(ATTR_CURRENT_PLAYER, currentPlayer);
                    response.redirect("/");
                    throw halt(200);
                } else { //username taken
                    vm.put(ATTR_MESSAGE, new Message(MSG_TAKEN_USERNAME, MessageType.error));
                }
            } else { //username invalid
                vm.put(ATTR_MESSAGE, new Message(MSG_INVALID_USERNAME, MessageType.error));
            }
        } else { //username not sent
            vm.put(ATTR_MESSAGE, new Message(MSG_MISSING_USERNAME, MessageType.error));
        }
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
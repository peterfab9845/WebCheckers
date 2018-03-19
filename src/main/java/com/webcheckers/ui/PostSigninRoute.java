package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
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

    public static final String MSG_MISSING_USERNAME = "You must provide a username";
    public static final String MSG_INVALID_USERNAME = "Invalid username; must be alphanumeric";
    public static final String MSG_TAKEN_USERNAME = "Username taken, please choose a different one";

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
        vm.put("title", "Sign-in");

        // retrieve request parameter
        final String username = request.queryParams("name");
        if (username != null) {
            boolean loginSuccess;
            if (username.matches("[A-Za-z0-9 ]+")) {
                Player currentPlayer = new Player(username);
                loginSuccess = PlayerLobby.addPlayer(currentPlayer, request.session());
                if (loginSuccess) { //everything worked
                    vm.put("currentPlayer", currentPlayer);
                    response.redirect("/", 200);
                    throw halt(200);
                } else { //username taken
                    vm.put("message", new Message(MSG_TAKEN_USERNAME, MessageType.error));
                }
            } else { //username invalid
                vm.put("message", new Message(MSG_INVALID_USERNAME, MessageType.error));
            }
        } else { //username not sent
            vm.put("message", new Message(MSG_MISSING_USERNAME, MessageType.error));
        }
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
package com.webcheckers.ui;

import static spark.Spark.halt;

import com.google.gson.Gson;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to validate the player's moves.
 *
 * @author Adam Heeter
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /validateMove} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostValidateMoveRoute(final Gson gson) {
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        //
        this.gson = gson;
        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    /**
     * Handle the player's movement request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");
        return halt(200);
    }
}
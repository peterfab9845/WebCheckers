package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.ui.ai.PostAIRoute;
import com.webcheckers.ui.game.*;
import com.webcheckers.ui.home.GetHomeRoute;
import com.webcheckers.ui.movement.*;
import com.webcheckers.ui.saves.*;
import com.webcheckers.ui.signin.GetSigninRoute;
import com.webcheckers.ui.signin.GetSignoutRoute;
import com.webcheckers.ui.signin.PostResignRoute;
import com.webcheckers.ui.signin.PostSigninRoute;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.*;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {

    //
    // Constants
    //

    /**
    * The URL pattern to request the Home page.
    */
    private static final String HOME_URL = "/";

    /**
    * The URL pattern to request the Signin page.
    */
    private static final String SIGNING_URL = "/signin";

    /**
    * The URL pattern to request the Signout page.
    */
    private static final String SIGNOUT_URL = "/signout";

    /**
    * The URL pattern to request the Game page.
    */
    private static final String GAME_URL = "/game";

    /**
    * The URL pattern to resign from a game.
    */
    private static final String RESIGN_URL = "/resignGame";

    /**
    * The URL pattern to request the move validation Ajax action.
    */
    private static final String VALIDATE_MOVE_URL = "/validateMove";

    /**
    * The URL pattern to request the backup move Ajax action.
    */
    private static final String BACKUP_MOVE_URL = "/backupMove";

    /**
    * The URL pattern to request the submit turn Ajax action.
    */
    private static final String SUBMIT_TURN_URL = "/submitTurn";

    /**
    * The URL pattern to request the check turn Ajax action.
    */
    private static final String CHECK_TURN_URL = "/checkTurn";

    /**
     * The URL pattern to request the saves url.
     */
    private static final String SAVES_URL = "/saves";

    /**
     * The URL pattern to request the end url.
     */
    private static final String END_URL = "/end";

    private static final String AI_URL = "/ai";

    private static final String SPECTATE_URL = "/spectate";

    private static final String QUIT_SIGNIN = "/leave";

    private static final String SAVE_URL = "/save";

    private static final String WATCH__URL = "/watch";

    private static final String SPEED_URL = "/speed";

    private static final String PLAY_URL = "/play";

    /**
     * Gson object for transporting data
     */
    private final Gson gson;

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
    * The constructor for the Web Server.
    *
    * @param templateEngine
    *    The default {@link TemplateEngine} to render page-level HTML views.
    * @param gson
    *    The Google JSON parser object used to render Ajax responses.
    *
    * @throws NullPointerException
    *    If any of the parameters are {@code null}.
    */
    public WebServer(final TemplateEngine templateEngine, final Gson gson) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gson, "gson must not be null");
        //
        this.templateEngine = templateEngine;
        this.gson = gson;
        this.playerLobby = new PlayerLobby();
    }

    //
    // Public methods
    //

    /**
    * Initialize all of the HTTP routes that make up this web application.
    *
    * <p>
    * Initialization of the web server includes defining the location for static
    * files, and defining all routes for processing client requests. The method
    * returns after the web server finishes its initialization.
    * </p>
    */
    public void initialize() {

        // Configuration to serve static files
        staticFileLocation("/public");

        //// Setting any route (or filter) in Spark triggers initialization of the
        //// embedded Jetty web server.

        //// A route is set for a request verb by specifying the path for the
        //// request, and the function callback (request, response) -> {} to
        //// process the request. The order that the routes are defined is
        //// important. The first route (request-path combination) that matches
        //// is the one which is invoked. Additional documentation is at
        //// http://sparkjava.com/documentation.html and in Spark tutorials.

        //// Each route (processing function) will check if the request is valid
        //// from the client that made the request. If it is valid, the route
        //// will extract the relevant data from the request and pass it to the
        //// application object delegated with executing the request. When the
        //// delegate completes execution of the request, the route will create
        //// the parameter map that the response template needs. The data will
        //// either be in the value the delegate returns to the route after
        //// executing the request, or the route will query other application
        //// objects for the data needed.

        //// FreeMarker defines the HTML response using templates. Additional
        //// documentation is at
        //// http://freemarker.org/docs/dgui_quickstart_template.html.
        //// The Spark FreeMarkerEngine lets you pass variable values to the
        //// template via a map. Additional information is in online
        //// tutorials such as
        //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

        //// These route definitions are examples. You will define the routes
        //// that are appropriate for the HTTP client interface that you define.
        //// Create separate Route classes to handle each route; this keeps your
        //// code clean; using small classes.


        get(HOME_URL, new GetHomeRoute(templateEngine, playerLobby));

        get(SIGNING_URL, new GetSigninRoute(templateEngine, playerLobby));

        post(SIGNING_URL, new PostSigninRoute(templateEngine, playerLobby));

        get(GAME_URL, new GetGameRoute(templateEngine,playerLobby));

        post(VALIDATE_MOVE_URL, new PostValidateMoveRoute(gson, playerLobby));

        post(SUBMIT_TURN_URL, new PostSubmitTurnRoute(gson, playerLobby));

        post(CHECK_TURN_URL, new PostCheckTurnRoute(gson, playerLobby));

        post(BACKUP_MOVE_URL, new PostBackupMoveRoute(gson, playerLobby));

        get(SIGNOUT_URL, new GetSignoutRoute(playerLobby));

        get(SAVES_URL, new GetSavesRoute(templateEngine, playerLobby));

        get(END_URL, new GetEndRoute(templateEngine, playerLobby));

        post(END_URL, new PostEndRoute(gson, playerLobby));

        get(AI_URL, new PostAIRoute(gson, playerLobby));

        get(SPECTATE_URL, new GetSpectatingRoute(templateEngine, playerLobby));

        post(RESIGN_URL, new PostResignRoute(gson, playerLobby));

        post(QUIT_SIGNIN, new PostLeaveSpectatingRoute(gson, playerLobby));

        post(SAVE_URL, new PostSaveRoute(gson, playerLobby));

        get(WATCH__URL, new GetWatchRoute(templateEngine,playerLobby));

        post(SPEED_URL, new PostSpeedRoute(gson, playerLobby));

        post(PLAY_URL, new PostPlayRoute(gson, playerLobby));

        // /
        LOG.config("WebServer is initialized.");
    }

}
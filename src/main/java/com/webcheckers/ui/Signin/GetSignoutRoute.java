package com.webcheckers.ui.Signin;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Player;
import com.webcheckers.ui.Game.GetGameRoute;
import com.webcheckers.ui.Home.GetHomeRoute;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetSignoutRoute implements Route{


    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Constructor
     * @param playerLobby
     */
    public GetSignoutRoute(PlayerLobby playerLobby) {

        this.playerLobby = playerLobby;
        //
        LOG.config("GetSigninRoute is initialized.");

    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Player user = playerLobby.getPlayer(request.session());
        if(user != null)
            playerLobby.removePlayer(user, request.session());
        response.redirect("/");
        throw halt(200);

    }
}

package com.webcheckers.ui.Signin;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Player;
import com.webcheckers.ui.Home.GetHomeRoute;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetSignoutRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetSignoutRoute.class.getName());
    private PlayerLobby playerLobby;

    public GetSignoutRoute(PlayerLobby playerLobby) {

        this.playerLobby = playerLobby;
        //
        LOG.config("GetSigninRoute is initialized.");

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Player user = playerLobby.getPlayer(request.session());
        if(user != null)
            playerLobby.removePlayer(user, request.session());
        response.redirect("/");
        throw halt(200);

    }
}

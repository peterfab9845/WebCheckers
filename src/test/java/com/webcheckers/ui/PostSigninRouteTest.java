package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

@Tag("UI-tier")
public class PostSigninRouteTest {

    private PostSigninRoute CuT;

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;

}

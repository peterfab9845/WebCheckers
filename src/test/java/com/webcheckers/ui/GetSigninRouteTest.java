package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.get;
import static spark.Spark.halt;

class GetSigninRouteTest {

    private static final String VALID_USERNAME = "MyUsername";
    private static final String SESSION_ID = "sessionId";

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Player player;
    private TemplateEngineTester testHelper;

    @BeforeEach
    void setup(){
        request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        player = mock(Player.class);
        when(player.getName()).thenReturn(VALID_USERNAME);
        PlayerLobby.init();
        testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    }

    @Test
    void constructor(){
        engine = mock(TemplateEngine.class);
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);
    }

    @Test
    void constructor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final GetHomeRoute getHomeRoute = new GetHomeRoute(engine);
        }, "GetHomeRoute allowed null template engine.");
    }

    @Test
    void handleWithoutTakenSession() {

        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);
        getSigninRoute.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Sign-in");

    }

    @Test
    void handleWithTakenSession(){


        PlayerLobby.addPlayer(player, request.session());
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);
        try { getSigninRoute.handle(request, response); }
        catch (HaltException ignored){}

    }
}
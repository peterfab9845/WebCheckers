package com.webcheckers.ui;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.get;
import static spark.Spark.halt;

class GetSigninRouteTest {

    private static final String USER_NAME1 = "Andrew";
    private static final String USER_NAME2 = "Peter";
    private static final String SESSION_ID = "sessionId";

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Player player1;
    private Player player2;
    private Message message;

    @Test
    void constructor(){
        engine = mock(TemplateEngine.class);
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);
    }

    @Test
    void handleWithoutTakenUsername() {
        request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(USER_NAME1);
        PlayerLobby.init();
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign-in");

        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);

        Object expected = engine.render(new ModelAndView(vm, "signin.ftl"));
        Object actual = getSigninRoute.handle(request, response);

        assertEquals(actual, expected);
    }

    @Test
    void handleWithTakenUsername(){
        request = mock(Request.class);
        Session session = mock(Session.class);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(USER_NAME1);

        PlayerLobby.init();
        PlayerLobby.addPlayer(player1, session);
        when(request.session()).thenReturn(session);

        GetSigninRoute getSigninRoute = new GetSigninRoute(engine);
        try {
            getSigninRoute.handle(request, response);
        }
        catch (HaltException e){
            e.printStackTrace();
        }
    }
}
package com.webcheckers.ui.Signin;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")
class GetSigninRouteTest {

    private static final String VALID_USERNAME = "MyUsername";
    private static final String SESSION_ID = "sessionId";

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Player player;
    private TemplateEngineTester testHelper;
    private PlayerLobby lobby;

    @BeforeEach
    void setup() {
        request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        player = mock(Player.class);
        when(player.getName()).thenReturn(VALID_USERNAME);
        when(player.getSession()).thenReturn(session);
        lobby = new PlayerLobby();
        testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    }

    @Test
    void constructor() {
        engine = mock(TemplateEngine.class);
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine, lobby);
    }

    @Test
    void constructor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final GetSigninRoute getHomeRoute = new GetSigninRoute(engine, lobby);
        }, "GetSigninRoute allowed null template engine.");
    }

    @Test
    void handleWithoutTakenSession() {

        GetSigninRoute getSigninRoute = new GetSigninRoute(engine, lobby);
        getSigninRoute.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Sign-In");

    }

    @Test
    void handleWithTakenSession() {

        lobby.addPlayer(player);
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine, lobby);
        try {
            getSigninRoute.handle(request, response);
        } catch (HaltException ignored) {
        }

    }

    @Test
    void handleHalt() {
        lobby.addPlayer(player);
        GetSigninRoute getSigninRoute = new GetSigninRoute(engine, lobby);
        assertThrows(HaltException.class, () -> getSigninRoute.handle(request, response));
    }
}
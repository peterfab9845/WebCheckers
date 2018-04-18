package com.webcheckers.ui.home;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.playerlobby.PlayerLobby;
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

/**
 * Test class for GetHomeRoute
 */
@SuppressWarnings("WeakerAccess")
@Tag("UI-tier")
public class GetHomeRouteTest {

    private static final String SESSION_ID = "sessionId";

    private static final String PLAYER_NAME = "playerName";

    private TemplateEngine engine;

    private Request request;

    private Response response;

    private Session session;

    private PlayerLobby lobby;

    /**
     * Set up request, session, engine, and lobby to use for tests
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby(); // friendly
    }

    /**
     * Test that the constructor will not accept a null template engine
     */
    @Test
    public void constructor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final GetHomeRoute getHomeRoute = new GetHomeRoute(engine, lobby);
        }, "GetHomeRoute allowed null template engine.");
    }

    /**
     * Test behavior for when a player is not signed in
     */
    @Test
    public void handle_notSignedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine, lobby);
        getHomeRoute.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("currentPlayer", null);
        testHelper.assertViewModelAttribute("playerCount", 0);
        testHelper.assertViewModelAttributeIsAbsent("playerList");
    }

    /**
     * Test behavior for when a player is signed in
     */
    @Test
    public void handle_signedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = mock(Player.class);
        when(player.isInGame()).thenReturn(false);
        when(player.getName()).thenReturn(PLAYER_NAME);
        when(player.getSession()).thenReturn(session);
        when(player.isInLobby()).thenReturn(true);
        lobby.addPlayer(player);
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine, lobby);
        getHomeRoute.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("currentPlayer", player);
        testHelper.assertViewModelAttribute("playerCount", 1);
    }

    /**
     * Test for redirect if in a game
     */
    @Test
    void handle_inGame() {
        Player player = mock(Player.class);
        when(player.getSession()).thenReturn(session);
        when(player.isInLobby()).thenReturn(false);
        lobby.addPlayer(player);
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine, lobby);
        assertThrows(HaltException.class, () -> getHomeRoute.handle(request, response));
    }
}
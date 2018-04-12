package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.Message;
import com.webcheckers.appl.MessageMap;
import com.webcheckers.appl.PlayerLobby.PlayerLobby;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")
public class GetHomeRouteTest {
    private static final String SESSION_ID = "sessionId";
    
    private static final String PLAYER_NAME = "playerName";
    
    private static final String MESSAGE_TEXT = "message";
    
    private TemplateEngine engine;
    
    private Request request;
    
    private Response response;
    
    private Session session;
    
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        PlayerLobby.init();
        MessageMap.init();
    }
    
    @Test
    public void constructor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final GetHomeRoute getHomeRoute = new GetHomeRoute(engine);
        }, "GetHomeRoute allowed null template engine.");
    }
    
    @Test
    public void handle_notSignedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine);
        getHomeRoute.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("currentPlayer", null);
        testHelper.assertViewModelAttribute("playerCount", 0);
        testHelper.assertViewModelAttributeIsAbsent("playerList");
        testHelper.assertViewModelAttributeIsAbsent("message");
    }
    
    @Test
    public void handle_signedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = mock(Player.class);
        when(player.isInGame()).thenReturn(false);
        when(player.getName()).thenReturn(PLAYER_NAME);
        PlayerLobby.addPlayer(player, session);
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine);
        getHomeRoute.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("currentPlayer", player);
        testHelper.assertViewModelAttribute("playerCount", 1);
        testHelper.assertViewModelAttribute("playerList", PlayerLobby.getPlayerListExcept(PLAYER_NAME));
        testHelper.assertViewModelAttributeIsAbsent("message");
    }
    
    @Test
    public void handle_signedInWithMessage() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = mock(Player.class);
        when(player.isInGame()).thenReturn(false);
        when(player.getName()).thenReturn(PLAYER_NAME);
        PlayerLobby.addPlayer(player, session);
        Message message = new Message(MESSAGE_TEXT, MessageType.info);
        MessageMap.setMessage(session, message);
        GetHomeRoute getHomeRoute = new GetHomeRoute(engine);
        getHomeRoute.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("currentPlayer", player);
        testHelper.assertViewModelAttribute("playerCount", 1);
        testHelper.assertViewModelAttribute("playerList", PlayerLobby.getPlayerListExcept(PLAYER_NAME));
        testHelper.assertViewModelAttribute("message", message);
    }
}
package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")
public class PostSigninRouteTest {

    private static final String INVALID_USERNAME = "MyName123!";
    private static final String VALID_USERNAME = "MyUsername";

    private PostSigninRoute CuT;

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private PlayerLobby lobby;
    private Player player;
    private Message message;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);
        player = mock(Player.class);

        CuT = new PostSigninRoute(engine);
    }

    @Test
    public void ctor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final PostSigninRoute CuT = new PostSigninRoute(engine);
        }, "PostSigninRoute allowed null template engine.");
    }

    @Test
    public void handle_noUsername() {
        when(request.queryParams("name")).thenReturn(null);
        message = new Message(PostSigninRoute.MSG_MISSING_USERNAME, MessageType.error);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign-in");
        testHelper.assertViewModelAttribute("message", message);
        testHelper.assertViewModelAttributeIsAbsent("currentPlayer");
    }

    @Test
    public void handle_invalidUsername() {
        when(request.queryParams("name")).thenReturn(INVALID_USERNAME);
        message = new Message(PostSigninRoute.MSG_INVALID_USERNAME, MessageType.error);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign-in");
        testHelper.assertViewModelAttribute("message", message);
        testHelper.assertViewModelAttributeIsAbsent("currentPlayer");
    }

    @Test
    public void handle_takenUsername() {
        when(request.queryParams("name")).thenReturn(VALID_USERNAME);
        message = new Message(PostSigninRoute.MSG_USERNAME_TAKEN, MessageType.error);
        when(lobby.addPlayer(any(Player.class), any(Session.class))).thenReturn(false);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign-in");
        testHelper.assertViewModelAttribute("message", message);
        testHelper.assertViewModelAttributeIsAbsent("currentPlayer");
    }

    @Test
    public void handle_goodUsername() {
        when(request.queryParams("name")).thenReturn(VALID_USERNAME);
        when(player.getName()).thenReturn(VALID_USERNAME);
        when(lobby.addPlayer(any(Player.class), any(Session.class))).thenReturn(true);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttributeIsAbsent("message");
        testHelper.assertViewModelAttribute("currentPlayer", player);

        verify(response).redirect("/", 200);
    }
}

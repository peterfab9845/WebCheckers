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
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

@SuppressWarnings("WeakerAccess")
@Tag("UI-tier")
public class PostSigninRouteTest {

    private static final String INVALID_USERNAME = "MyName123!";
    private static final String VALID_USERNAME = "MyUsername";
    private static final String SESSION_ID = "sessionId";

    private PostSigninRoute CuT;

    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Player player;
    private Message message;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn(SESSION_ID);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        player = mock(Player.class);
        when(player.getName()).thenReturn(VALID_USERNAME);
        PlayerLobby.init(); //friendly

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
    public void handle_noUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_MISSING_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, "signin.ftl");

        vm.put("title", "Sign-in");
        vm.put("message", message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains("<title>Sign-in | Web Checkers</title>"),
            "Title tag missing from HTML.");
        assertTrue(viewHTML.contains("<div class=\"error\">" +
                PostSigninRoute.MSG_MISSING_USERNAME + "</div>"),
            "Error message tag missing from HTML.");
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
    public void handle_invalidUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_INVALID_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, "signin.ftl");

        vm.put("title", "Sign-in");
        vm.put("message", message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains("<title>Sign-in | Web Checkers</title>"),
            "Title tag missing from HTML.");
        assertTrue(viewHTML.contains("<div class=\"error\">" +
                PostSigninRoute.MSG_INVALID_USERNAME + "</div>"),
            "Error message tag missing from HTML.");
    }

    @Test
    public void handle_takenUsername() {
        when(request.queryParams("name")).thenReturn(VALID_USERNAME);
        message = new Message(PostSigninRoute.MSG_TAKEN_USERNAME, MessageType.error);
        PlayerLobby.addPlayer(player, request.session());

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
    public void handle_takenUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_TAKEN_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, "signin.ftl");

        vm.put("title", "Sign-in");
        vm.put("message", message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains("<title>Sign-in | Web Checkers</title>"),
            "Title tag missing from HTML.");
        assertTrue(viewHTML.contains("<div class=\"error\">" +
                PostSigninRoute.MSG_TAKEN_USERNAME + "</div>"),
            "Error message tag missing from HTML.");
    }

    @Test
    public void handle_goodUsername() {
        when(request.queryParams("name")).thenReturn(VALID_USERNAME);
        when(player.getName()).thenReturn(VALID_USERNAME);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        boolean exceptionThrown = false;

        try {
            CuT.handle(request, response);

            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();

            testHelper.assertViewModelAttributeIsAbsent("message");
            testHelper.assertViewModelAttribute("currentPlayer", player);

            verify(response).redirect("/", 200);
        } catch (HaltException e) {
            // halt(200) is expected for redirect to homepage
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            fail("handle did not halt rendering of page for redirect.");
        }
    }

    //no HTML test for this condition; this is handled by GetHomeRoute
}

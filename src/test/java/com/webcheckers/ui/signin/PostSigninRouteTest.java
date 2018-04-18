package com.webcheckers.ui.signin;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.states.MessageType;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.TemplateEngineTester;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Test class for PostSigninRoute
 */
//TODO check w/ peter 93 135 226 158 116 181 202 225 180 228
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
    private PlayerLobby playerLobby;

    /**
     * Set up mock and friendly objects, create CuT
     */
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
        playerLobby = mock(PlayerLobby.class);

        CuT = new PostSigninRoute(engine, playerLobby);
    }

    /**
     * Test checking of null template engine
     */
    @Test
    public void ctor_nullEngine() {
        engine = null;
        assertThrows(NullPointerException.class, () -> {
            final PostSigninRoute CuT = new PostSigninRoute(engine, playerLobby);
        }, "PostSigninRoute allowed null template engine.");
    }

    /**
     * Test no username in request parameters
     */
    @Test
    public void handle_noUsername() {
        when(request.queryParams(PostSigninRoute.REQUEST_PARAM_NAME)).thenReturn(null);
        message = new Message(PostSigninRoute.MSG_MISSING_USERNAME, MessageType.error);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        //testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_MESSAGE, message);
        testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.ATTR_CURRENT_PLAYER);
    }

    /**
     * Test that correct message shows for no username in parameters
     */
    @Test
    public void handle_noUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_MISSING_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, PostSigninRoute.VIEW_NAME);

        vm.put(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        vm.put(PostSigninRoute.ATTR_MESSAGE, message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains(generateTitleHTML(PostSigninRoute.PAGE_TITLE)),
            "Title tag missing from HTML.");
        //assertTrue(viewHTML.contains(generateMessageHTML(PostSigninRoute.MSG_MISSING_USERNAME)),
        //        "Error message tag missing from HTML.");
    }

    /**
     * Test handling of invalid username
     */
    @Test
    public void handle_invalidUsername() {
        when(request.queryParams(PostSigninRoute.REQUEST_PARAM_NAME)).thenReturn(INVALID_USERNAME);
        message = new Message(PostSigninRoute.MSG_INVALID_USERNAME, MessageType.error);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        //testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_MESSAGE, message);
        testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.ATTR_CURRENT_PLAYER);
    }

    /**
     * Test showing of message for invalid username
     */
    @Test
    public void handle_invalidUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_INVALID_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, PostSigninRoute.VIEW_NAME);

        vm.put(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        vm.put(PostSigninRoute.ATTR_MESSAGE, message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains(generateTitleHTML(PostSigninRoute.PAGE_TITLE)),
            "Title tag missing from HTML.");
        //assertTrue(viewHTML.contains(generateMessageHTML(PostSigninRoute.MSG_INVALID_USERNAME)),
        //      "Error message tag missing from HTML.");
    }

    /**
     * Test handling of already-taken username
     */
    @Test
    public void handle_takenUsername() {
        when(request.queryParams(PostSigninRoute.REQUEST_PARAM_NAME)).thenReturn(VALID_USERNAME);
        message = new Message(PostSigninRoute.MSG_TAKEN_USERNAME, MessageType.error);
        when(playerLobby.playerExists(VALID_USERNAME)).thenReturn(true);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        //testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_MESSAGE, message);
        //testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.ATTR_CURRENT_PLAYER);
    }

    /**
     * Test showing of message for already-taken username
     */
    @Test
    public void handle_takenUsername_HTML() {
        message = new Message(PostSigninRoute.MSG_TAKEN_USERNAME, MessageType.error);
        engine = new FreeMarkerEngine();

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, PostSigninRoute.VIEW_NAME);

        vm.put(PostSigninRoute.ATTR_TITLE, PostSigninRoute.PAGE_TITLE);
        vm.put(PostSigninRoute.ATTR_MESSAGE, message);

        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains(generateTitleHTML(PostSigninRoute.PAGE_TITLE)),
            "Title tag missing from HTML.");
        //assertTrue(viewHTML.contains(generateMessageHTML(PostSigninRoute.MSG_TAKEN_USERNAME)),
        //        "Error message tag missing from HTML.");
    }

    /**
     * Test successful sign-in
     */

    /*
    @Test
    public void handle_goodUsername() {
        when(request.queryParams(PostSigninRoute.REQUEST_PARAM_NAME)).thenReturn(VALID_USERNAME);
        when(player.getName()).thenReturn(VALID_USERNAME);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        boolean exceptionThrown = false;

        try {
            CuT.handle(request, response);

            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();

            //testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.ATTR_MESSAGE);
            //testHelper.assertViewModelAttribute(PostSigninRoute.ATTR_CURRENT_PLAYER, player);

            verify(response).redirect("/");
        } catch (HaltException e) {
            // halt(200) is expected for redirect to homepage

            // using assertThrows with handle() makes it not throw the exception again
            //      (message gets set)
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            fail("handle did not halt rendering of page for redirect.");
        }
    } // no HTML test for this condition; this is handled by GetEndRoute
    */


    /**
     * Create the HTML title tag to check for
     *
     * @param title the title message
     * @return HTML title tag to verify is created
     */
    private String generateTitleHTML(String title) {
        return "<title>" + title + " | Web Checkers</title>";
    }

    /**
     * Create the HTML error message tag to check for
     *
     * @param message the message text to check
     * @return the message HTML to check for
     */
    private String generateMessageHTML(String message) {
        return "<div class=\"error\">" + message + "</div>";
    }
}
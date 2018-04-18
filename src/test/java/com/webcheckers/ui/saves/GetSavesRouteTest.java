package com.webcheckers.ui.saves;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.appl.playerlobby.PlayerLobby;

import spark.HaltException;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

/**
 * Test class for GetSavesRoute
 */
@Tag("UI-tier")
public class GetSavesRouteTest {
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private GetSavesRoute route;
    private Request request;
    private Response response;
    
    /**
     * Set up variables for the route
     */
    @BeforeEach
    public void setup() {
        engine = mock(TemplateEngine.class);
        lobby = mock(PlayerLobby.class);
        route = new GetSavesRoute(engine, lobby);
        request = mock(Request.class);
        response = mock(Response.class);
    }
    
    /**
     * Test handle without a game
     */
    @Test
    public void handle_withoutGame() {
        assertThrows(HaltException.class, () -> {
            route.handle(request, response);
        }, "GetSavesRoute allowed null game");
    }
}
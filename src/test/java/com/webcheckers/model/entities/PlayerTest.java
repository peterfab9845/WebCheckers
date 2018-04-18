package com.webcheckers.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.gamesaves.GameLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Session;

class PlayerTest {

    private Player CuT;
    private Session session;
    private GameLog gameLog;
    private final String USERNAME = "User";

    @BeforeEach
    void setUp() {
        session = mock(Session.class);
        gameLog = mock(GameLog.class);
        when(gameLog.toString()).thenReturn("Yeah this is happening");
        CuT = new Player(USERNAME, session);
    }

    @Test
    void getSession() {
        Session sessionTest = CuT.getSession();
        assertEquals(sessionTest, session);
    }

    @Test
    void getGameLogs() {
        CuT.getGameLogs();
    }

    @Test
    void deleteGame() {
        CuT.saveGame(mock(GameLog.class));
        CuT.saveGame(gameLog);
        CuT.deleteGame(gameLog.toString());
        CuT.deleteGame(null);
    }

    @Test
    void saveGame() {
        CuT.saveGame(gameLog);
    }

    @Test
    void hashCode_Test() {
        CuT.hashCode();
    }


}
package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Session;

@Tag("Application-tier")
public class PlayerLobbyTest {

    private static final String PLAYER1_USERNAME = "Player1 Username";
    private static final String PLAYER2_USERNAME = "Player2 Username";
    private static final String PLAYER1_SESSIONID = "player1sessionid";
    private static final String PLAYER2_SESSIONID = "player2sessionid";

    private Player player1;
    private Player player2;
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setup() {
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(PLAYER1_USERNAME);
        player2 = mock(Player.class);
        when(player2.getName()).thenReturn(PLAYER2_USERNAME);
        session1 = mock(Session.class);
        when(session1.id()).thenReturn(PLAYER1_SESSIONID);
        session2 = mock(Session.class);
        when(session2.id()).thenReturn(PLAYER2_SESSIONID);

        PlayerLobby.init();
    }

    @Test
    public void addPlayer_noConflict() {
        assertTrue(PlayerLobby.addPlayer(player1, session1));
        assertTrue(PlayerLobby.addPlayer(player2, session2));
    }

    @Test
    public void addPlayer_conflict() {
        assertTrue(PlayerLobby.addPlayer(player1, session1));
        assertFalse(PlayerLobby.addPlayer(player1, session1));
        assertFalse(PlayerLobby.addPlayer(player2, session1));
    }

}

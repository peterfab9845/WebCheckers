package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
        assertTrue(PlayerLobby.addPlayer(player1, session1),
            "Could not add first player.");
        assertTrue(PlayerLobby.addPlayer(player2, session2),
            "Could not add second player.");
    }

    @Test
    public void addPlayer_conflict() {
        PlayerLobby.addPlayer(player1, session1);

        assertFalse(PlayerLobby.addPlayer(player1, session1),
            "addPlayer accepted same player twice (same session id).");
        assertFalse(PlayerLobby.addPlayer(player1, session2),
            "addPlayer accepted same player twice (different session id)");
    }

    @Test
    public void getNextPlayer_noPlayers() {
        assertNull(PlayerLobby.getNextPlayer(),
            "getNextPlayer did not return null with no players.");
    }

    @Test
    public void getNextPlayer_onePlayer() {
        PlayerLobby.addPlayer(player1, session1);

        assertEquals(player1, PlayerLobby.getNextPlayer());
    }

    @Test
    public void getNextPlayer_twoPlayers() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        Player return1 = PlayerLobby.getNextPlayer();
        Player return2 = PlayerLobby.getNextPlayer();

        if (player1.equals(return1) && player2.equals(return2)) {
            return;
        }
        if (player2.equals(return1) && player1.equals(return1)) {
            return;
        }
        fail("getNextPlayer returned same player twice or an un-added player.");
    }

}

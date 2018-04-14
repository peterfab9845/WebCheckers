package com.webcheckers.appl.PlayerLobby;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Session;

/**
 * Test class for PlayerManager
 */
@SuppressWarnings("WeakerAccess")
@Tag("Application-tier")
public class PlayerManagerTest {
    
    private static String PLAYER1_NAME = "Player1 name";
    private static String PLAYER2_NAME = "Player2 name";
    private static String PLAYER1_SESSID = "p1id";
    private static String PLAYER2_SESSID = "p2id";
    
    private PlayerManager CuT;
    
    private Player player1;
    private Session player1Session;
    private Player player2;
    private Session player2Session;

    /**
     * Set up a new PlayerManager and some players for each test
     */
    @BeforeEach
    public void setup() {
        CuT = new PlayerManager();
        
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(PLAYER1_NAME);
        player1Session = mock(Session.class);
        when(player1Session.id()).thenReturn(PLAYER1_SESSID);
        when(player1.getSession()).thenReturn(player1Session);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn(PLAYER2_NAME);
        player2Session = mock(Session.class);
        when(player2Session.id()).thenReturn(PLAYER2_SESSID);
        when(player2.getSession()).thenReturn(player2Session);
    }

    /**
     * Test adding players to the manager
     */
    @Test
    public void addPlayer() {
        //CuT.addPlayer()
    }
}

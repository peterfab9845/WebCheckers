package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Player;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Session;

/**
 * Test class for PlayerLobby
 */
@SuppressWarnings("WeakerAccess")
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

    /**
     * Create mock classes for testing, setup friendlies
     */
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


    /**
     * Does nothing; to get 100% coverage, the class must be instantiated,
     * even though it has only static methods
     */
    @Test
    public void instantiateForCoverage(){
        PlayerLobby playerLobby = new PlayerLobby();
    }

    /**
     * Test adding a player not already added
     */
    @Test
    public void addPlayer_noConflict() {
        assertTrue(PlayerLobby.addPlayer(player1, session1),
            "Could not add first player.");
        assertTrue(PlayerLobby.addPlayer(player2, session2),
            "Could not add second player.");
    }

    /**
     * Test adding the same player twice
     */
    @Test
    public void addPlayer_conflict() {
        PlayerLobby.addPlayer(player1, session1);

        assertFalse(PlayerLobby.addPlayer(player1, session1),
            "addPlayer accepted same player twice (same session id).");
        assertFalse(PlayerLobby.addPlayer(player1, session2),
            "addPlayer accepted same player twice (different session id).");
    }

    /**
     * Test getNextPlayer with no players added
     */
    @Test
    public void getNextPlayer_noPlayers() {
        assertNull(PlayerLobby.getNextPlayer(),
            "getNextPlayer did not return null with no players.");
    }

    /**
     * Test getNextPlayer with only one player added
     */
    @Test
    public void getNextPlayer_onePlayer() {
        PlayerLobby.addPlayer(player1, session1);

        assertEquals(player1, PlayerLobby.getNextPlayer(),
            "getNextPlayer did not return correct player with only one added.");
    }

    /**
     * Test getNextPlayer with more than one player added
     */
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

    /**
     * Test sessionExists with no players
     */
    @Test
    public void sessionExists_notAdded() {
        assertFalse(PlayerLobby.sessionExists(session1),
            "sessionExists returned true for un-added session.");
        assertFalse(PlayerLobby.sessionExists(session2),
            "sessionExists returned true for un-added session.");
    }

    /**
     * Test sessionExists with sessions added and then removed before test
     */
    @Test
    public void sessionExists_addedRemoved() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);
        PlayerLobby.getNextPlayer();
        PlayerLobby.getNextPlayer();

        assertFalse(PlayerLobby.sessionExists(session1),
            "sessionExists returned true for removed session.");
        assertFalse(PlayerLobby.sessionExists(session2),
            "sessionExists returned true for removed session.");
    }

    /**
     * Test sessionExists with players added and not removed
     */
    @Test
    public void sessionExists_added() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        assertTrue(PlayerLobby.sessionExists(session1),
            "sessionExists returned false for added session.");
        assertTrue(PlayerLobby.sessionExists(session2),
            "sessionExists returned false for added session.");
    }

    /**
     * Test getPlayer with no players added
     */
    @Test
    public void getPlayer_notAdded() {
        assertNull(PlayerLobby.getPlayer(session1),
            "getPlayer returned non-null for un-added session id.");
        assertNull(PlayerLobby.getPlayer(session2),
            "getPlayer returned non-null for un-added session id.");
    }

    /**
     * Test getPlayer with players added
     */
    @Test
    public void getPlayer_added() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        assertEquals(player1, PlayerLobby.getPlayer(session1),
            "getPlayer did not return correct player for first session id.");
        assertEquals(player2, PlayerLobby.getPlayer(session2),
            "getPlayer did not return correct player for second session id.");
    }

    /**
     * Test playerExists with no players added yet
     */
    @Test
    public void playerExists_notAdded() {
        assertFalse(PlayerLobby.playerExists(player1.getName()),
            "playerExists returned true for un-added player.");
        assertFalse(PlayerLobby.playerExists(player2.getName()),
            "playerExists returned true for un-added player.");
    }

    /**
     * Test playerExists with players added and then removed
     */
    @Test
    public void playerExists_addedRemoved() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);
        PlayerLobby.getNextPlayer();
        PlayerLobby.getNextPlayer();

        assertFalse(PlayerLobby.playerExists(player1.getName()),
            "playerExists returned true for removed player.");
        assertFalse(PlayerLobby.playerExists(player2.getName()),
            "playerExists returned true for removed player.");
    }

    /**
     * Test playerExists with players added and not removed
     */
    @Test
    public void playerExists_added() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        assertTrue(PlayerLobby.playerExists(player1.getName()),
            "playerExists returned false for added player.");
        assertTrue(PlayerLobby.playerExists(player2.getName()),
            "playerExists returned false for added player.");
    }

    /**
     * Test getPlayerByName with a username not added to the map
     */
    @Test
    public void getPlayerByName_notAdded() {
        assertNull(PlayerLobby.getPlayerByName(player1.getName()),
            "getPlayerByName returned non-null for un-added username.");
        assertNull(PlayerLobby.getPlayerByName(player2.getName()),
            "getPlayerByName returned non-null for un-added username.");
    }

    /**
     * Test getPlayerByName with multiple players added
     */
    @Test
    public void getPlayerByName_added() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        assertEquals(player1, PlayerLobby.getPlayerByName(player1.getName()),
            "getPlayerByName did not return correct player for first username.");
        assertEquals(player2, PlayerLobby.getPlayerByName(player2.getName()),
            "getPlayerByName did not return correct player for second username.");
    }

    /**
     * Test that getPlayerList returns empty list with no players
     */
    @Test
    public void getPlayerList_noPlayers() {
        List<String> playerList = PlayerLobby.getPlayerList();
        assertEquals(0, playerList.size(),
            "getPlayerList returned non-empty list with no players.");
    }

    /**
     * Test that getPlayerList returns size 1 list with correct name for one player
     */
    @Test
    public void getPlayerList_onePlayer() {
        PlayerLobby.addPlayer(player1, session1);
        List<String> playerList = PlayerLobby.getPlayerList();

        assertTrue(playerList.contains(player1.getName()),
            "getPlayerList returned list without added player.");
        assertEquals(1, playerList.size(),
            "getPlayerList returned too many names.");
    }

    /**
     * Test that getPlayerList returns all players' names in the list
     */
    @Test
    public void getPlayerList_twoPlayers() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);
        List<String> playerList = PlayerLobby.getPlayerList();

        assertTrue(playerList.contains(player1.getName()),
            "getPlayerList returned list without added player.");
        assertTrue(playerList.contains(player2.getName()),
            "getPlayerList returned list without added player.");
        assertEquals(2, playerList.size(),
            "getPlayerList returned too many names.");
    }

    /**
     * Test getPlayerListExcept with exclusion of non-added name
     */
    @Test
    public void getPlayerListExcept_noPlayers() {
        List<String> playerList = PlayerLobby.getPlayerListExcept(player1.getName());
        assertEquals(0, playerList.size(),
            "getPlayerListExcept returned non-empty list with no players.");
    }

    /**
     * Test getPlayerListExcept with one player added, but excluded
     */
    @Test
    public void getPlayerListExcept_onePlayerExcepted() {
        PlayerLobby.addPlayer(player1, session1);
        List<String> playerList = PlayerLobby.getPlayerListExcept(player1.getName());

        assertEquals(0, playerList.size(),
            "getPlayerListExcept returned a name despite excluding only added player.");
    }

    /**
     * Test getPlayerListExcept with one player added and not excluded
     */
    @Test
    public void getPlayerListExcept_onePlayerNotExcepted() {
        PlayerLobby.addPlayer(player1, session1);
        List<String> playerList = PlayerLobby.getPlayerListExcept(player2.getName());

        assertTrue(playerList.contains(player1.getName()),
            "getPlayerListExcept returned list without non-excepted player.");
        assertEquals(1, playerList.size(),
            "getPlayerListExcept returned wrong number of names.");
    }

    /**
     * Test getPlayerListExcept with two players added and one excluded
     */
    @Test
    public void getPlayerListExcept_twoPlayers() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);
        List<String> playerList = PlayerLobby.getPlayerListExcept(player1.getName());

        assertTrue(playerList.contains(player2.getName()),
            "getPlayerListExcept returned list without non-excepted player.");
        assertEquals(1, playerList.size(),
            "getPlayerListExcept returned too many names.");
    }

    /**
     * Test getPlayerCount with no players added
     */
    @Test
    public void getPlayerCount_noPlayers() {
        assertEquals(0, PlayerLobby.getPlayerCount(),
            "getPlayerCount returned nonzero with zero added.");
    }

    /**
     * Test getPlayerCount with only one player added
     */
    @Test
    public void getPlayerCount_onePlayer() {
        PlayerLobby.addPlayer(player1, session1);

        assertEquals(1, PlayerLobby.getPlayerCount(),
            "getPlayerCount returned non-one with one added.");
    }

    /**
     * Test getPlayerCount with more than one player added
     */
    @Test
    public void getPlayerCount_twoPlayers() {
        PlayerLobby.addPlayer(player1, session1);
        PlayerLobby.addPlayer(player2, session2);

        assertEquals(2, PlayerLobby.getPlayerCount(),
            "getPlayerCount returned non-two with two added.");
    }

    /**
     * After all tests, reset PlayerLobby to cleanup
     */
    @AfterAll
    public static void cleanup() {
        PlayerLobby.init();
    }
}
package com.webcheckers.appl.playerlobby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import java.util.Iterator;
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

    private static String PLAYER1_NAME = "Player1 name";
    private static String PLAYER2_NAME = "Player2 name";
    private static String PLAYER3_NAME = "Player3 name";

    private PlayerLobby CuT;

    private Player player1;
    private Session player1Session;
    private Player player2;
    private Session player2Session;
    private Player player3;
    private Session player3Session;

    private Game game1;
    private Game game2;

    /**
     * Set up a new PlayerLobby, some players, and two games for each test
     */
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();

        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(PLAYER1_NAME);
        player1Session = mock(Session.class);
        when(player1Session.id()).thenReturn("player1ID");
        when(player1.getSession()).thenReturn(player1Session);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn(PLAYER2_NAME);
        player2Session = mock(Session.class);
        when(player2Session.id()).thenReturn("player2ID");
        when(player2.getSession()).thenReturn(player2Session);

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn(PLAYER3_NAME);
        player3Session = mock(Session.class);
        when(player3Session.id()).thenReturn("player3ID");
        when(player3.getSession()).thenReturn(player3Session);

        game1 = mock(Game.class);
        when(game1.getRedPlayer()).thenReturn(player1);
        when(game1.getWhitePlayer()).thenReturn(player2);

        game2 = mock(Game.class);
        when(game1.getRedPlayer()).thenReturn(player1);
        when(game1.getWhitePlayer()).thenReturn(player2);
    }

    /**
     * Test adding players to the manager
     */
    @Test
    public void addPlayer() {
        assertTrue(CuT.addPlayer(player1), "Could not add un-added player to lobby.");
        assertFalse(CuT.addPlayer(player1), "Was able to add the same player twice.");
        assertTrue(CuT.addPlayer(player2), "Could not add a different player after first.");
    }

    /**
     * Test checking if a player exists
     */
    @Test
    public void playerExists() {
        CuT.addPlayer(player1);

        assertTrue(CuT.playerExists(PLAYER1_NAME),
            "playerExists returned false for added player.");
        assertFalse(CuT.playerExists(PLAYER2_NAME),
            "playerExists returned false for non-added player.");
    }

    /**
     * Test removing a player from the manager
     */
    @Test
    public void removePlayer() {
        when(player1.isInLobby()).thenReturn(true);
        when(player1.isInGame()).thenReturn(false);

        when(player2.isInLobby()).thenReturn(false);
        when(player2.isInGame()).thenReturn(true);

        CuT.addPlayer(player1);
        CuT.addPlayer(player2);

        CuT.addGame(player1, game1);
        CuT.addGame(player2, game1);

        CuT.removePlayer(player1);
        assertFalse(CuT.playerExists(PLAYER1_NAME),
            "Player was not removed correctly.");
        assertTrue(CuT.playerExists(PLAYER2_NAME),
            "Second player did not exist after removing first player.");

        CuT.removePlayer(player2);
        assertFalse(CuT.playerExists(PLAYER2_NAME));
    }

    /**
     * Test getting a player by their username
     */
    @Test
    public void getPlayer_byName() {
        CuT.addPlayer(player1);

        assertEquals(player1, CuT.getPlayer(PLAYER1_NAME),
            "Did not get correct player using username.");
        assertNull(CuT.getPlayer(PLAYER2_NAME),
            "Got a player when requesting an un-added name.");
    }

    /**
     * Test getting a player by their session
     */
    @Test
    public void getPlayer_bySession() {
        CuT.addPlayer(player1);

        assertEquals(player1, CuT.getPlayer(player1Session),
            "Did not get correct player using session.");
        assertNull(CuT.getPlayer(player2Session),
            "Got a player when requesting an un-added session.");
    }

    /**
     * Test getting iterator of all players in the lobby
     */
    @Test
    public void getPlayersInLobby() {
        when(player1.isInLobby()).thenReturn(true);
        CuT.addPlayer(player1);

        Iterator<Player> players = CuT.getPlayersInLobby();

        assertEquals(player1, players.next(), "Did not get correct player.");
    }

    /**
     * Test getting players in the lobby except one
     */
    @Test
    public void getPlayersInLobbyExcept() {
        when(player1.isInLobby()).thenReturn(true);
        when(player1.isInGame()).thenReturn(false);

        when(player2.isInLobby()).thenReturn(true);
        when(player2.isInGame()).thenReturn(false);

        when(player3.isInLobby()).thenReturn(false);
        when(player3.isInGame()).thenReturn(true);

        CuT.addPlayer(player1);
        CuT.addPlayer(player2);
        CuT.addPlayer(player3);

        Iterator<Player> players = CuT.getPlayersInLobbyExcept(player1Session);

        assertEquals(player2, players.next(),
            "Got wrong player when excluding only others.");
        assertFalse(players.hasNext(), "Excluded player is in resulting iterator.");

    }

    /**
     * Test getting players in games except one
     */
    @Test
    public void getPlayersInGameExcept() {
        when(player1.isInLobby()).thenReturn(false);
        when(player1.isInGame()).thenReturn(true);

        when(player2.isInLobby()).thenReturn(false);
        when(player2.isInGame()).thenReturn(true);

        when(player3.isInLobby()).thenReturn(true);
        when(player3.isInGame()).thenReturn(false);

        CuT.addPlayer(player1);
        CuT.addPlayer(player2);
        CuT.addPlayer(player3);

        Iterator<Player> players = CuT.getPlayersInGameExcept(player1Session);

        assertEquals(player2, players.next(),
            "Got wrong player when excluding only others.");
        assertFalse(players.hasNext(), "Excluded player is in resulting iterator.");
    }

    /**
     * Test getting count of players in the lobby
     */
    @Test
    public void countInLobby() {
        when(player1.isInLobby()).thenReturn(true);
        when(player2.isInLobby()).thenReturn(true);
        when(player3.isInLobby()).thenReturn(false);
        CuT.addPlayer(player1);
        CuT.addPlayer(player2);
        CuT.addPlayer(player3);

        assertEquals(3, CuT.countOnline(), "Wrong count of players in lobby.");
    }

    /**
     * Test adding/getting a game/player combo with the manager
     */
    @Test
    public void addGet() {
        CuT.addGame(player1, game1);
        CuT.addGame(player2, game2);
        assertEquals(game1, CuT.getGame(player1), "Game 1 was not added correctly.");
        assertEquals(game2, CuT.getGame(player2), "Game 2 was not added correctly.");
    }

    /**
     * Test removing a game with red losing
     */
    @Test
    public void removeGame_redLoss() {
        CuT.addGame(player1, game1);
        CuT.addGame(player2, game1);

        CuT.removeGame(player1);

        assertNull(CuT.getGame(player1), "Game 1 was not removed.");
        assertNull(CuT.getGame(player2), "Game 1 was not removed.");

        verify(player1).justLost();
        verify(player2).justWon();
    }

    /**
     * Test removing a game with red winning
     */
    @Test
    public void removeGame_redWin() {
        CuT.addGame(player1, game1);
        CuT.addGame(player2, game1);

        CuT.removeGame(player2);

        assertNull(CuT.getGame(player1), "Game 1 was not removed.");
        assertNull(CuT.getGame(player2), "Game 1 was not removed.");

        verify(player1).justWon();
        verify(player2).justLost();
    }

    /**
     * Test player challenges
     */
    @Test
    public void challenge() {
        CuT.challenge(player1, player2);

        verify(player1).setInGame();
        verify(player2).setInGame();

        verify(player1).setTeamColor(PieceColor.RED);
        verify(player2).setTeamColor(PieceColor.WHITE);

        assertNotNull(CuT.getGame(player1), "Player 1 was not mapped to a game.");
        assertNotNull(CuT.getGame(player1), "Player 2 was not mapped to a game.");

        Game createdGame = CuT.getGame(player1);
        assertEquals(player1, createdGame.getRedPlayer(),
            "Player 1 was not added to the created game.");
        assertEquals(player2, createdGame.getWhitePlayer(),
            "Player 2 was not added to the created game.");
    }

}

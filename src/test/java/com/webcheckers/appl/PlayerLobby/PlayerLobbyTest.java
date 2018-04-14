package com.webcheckers.appl.PlayerLobby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Session;

class PlayerLobbyTest {

    private final String PLAYER1NAME = "Player 1";
    private final String PLAYER2NAME = "Player 2";
    private final String PLAYER3NAME = "Player 3";
    private final String BROKEN_USERNAME = "I TRIED";
    private final String PLAYER1SESSIONID = "1";
    private final String PLAYER2SESSIONID = "2";
    private final String PLAYER3SESSIONID = "3";
    private final int TOTAL_PLAYERS = 2;

    private PlayerLobby CuT;
    private Player player1;
    private Player player2;
    private Player player3;
    private Session player1Session;
    private Session player2Session;
    private Session player3Session;
    private Game game;


    @BeforeEach
    void setUp() {
        CuT = new PlayerLobby();

        //Players
        //Mocking
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        player3 = mock(Player.class);
        player1Session = mock(Session.class);
        player2Session = mock(Session.class);
        player3Session = mock(Session.class);
        game = mock(Game.class);

        //Whens
        when(player1Session.id()).thenReturn(PLAYER1SESSIONID);
        when(player2Session.id()).thenReturn(PLAYER2SESSIONID);
        when(player3Session.id()).thenReturn(PLAYER3SESSIONID);
        when(player1.getName()).thenReturn(PLAYER1NAME);
        when(player2.getName()).thenReturn(PLAYER2NAME);
        when(player3.getName()).thenReturn(PLAYER3NAME);
        when(player1.getSession()).thenReturn(player1Session);
        when(player2.getSession()).thenReturn(player2Session);
        when(player3.getSession()).thenReturn(player3Session);
        when(player1.isInLobby()).thenReturn(true);
        when(player2.isInLobby()).thenReturn(true);
        when(player3.isInLobby()).thenReturn(false);
        when(game.getRedPlayer()).thenReturn(player1);
        when(game.getWhitePlayer()).thenReturn(player2);

        CuT.addPlayer(player1, player1Session);
        CuT.addPlayer(player2, player2Session);
        CuT.addPlayer(player3, player3Session);
    }

    @Test
    void removePlayer() {
        CuT.challenge(player1, player2);
        CuT.removePlayer(player1);
        when(player2.isInLobby()).thenReturn(false);
        CuT.removePlayer(player2);
    }

    @Test
    void removeGame() {
        CuT.addGame(player1, game);
        CuT.addGame(player2, game);
        CuT.removeGame(player1);
    }

    @Test
    void getPlayersInLobby() {
        Iterator iterator = CuT.getPlayersInLobby();
        Player player = (Player) iterator.next();
//        assertEquals(player, player1);
        player = (Player) iterator.next();
//        assertEquals(player, player2);
//        assertEquals(player, player3);
    }

    @Test
    void getPlayersInLobbyExcept() {
        Iterator iterator = CuT.getPlayersInLobbyExcept(player1Session);
        Player player = (Player) iterator.next();
        assertEquals(player, player2);
    }

    @Test
    void playersInLobby() {
        int actual = CuT.playersInLobby();
        assertEquals(TOTAL_PLAYERS, actual);
    }

    @Test
    void playerExists() {
        boolean actual = CuT.playerExists(PLAYER1NAME);
        assertTrue(actual);
    }

    @Test
    void validUsername() {
        boolean actual = CuT.playerExists(BROKEN_USERNAME);
        assertFalse(actual);
        actual = CuT.validUsername(PLAYER1NAME);
        assertTrue(actual);
    }

    @Test
    void getPlayer_Session() {
        Player actual = CuT.getPlayer(player1Session);
        assertEquals(player1, actual);
    }

    @Test
    void getPlayer1() {
        Player actual = CuT.getPlayer(PLAYER2NAME);
        assertEquals(player2, actual);
        actual = CuT.getPlayer(BROKEN_USERNAME);
        assertEquals(null, actual);
    }

    @Test
    void challenge() {
        CuT.challenge(player1, player2);
    }

    @Test
    void getGame() {
        CuT.addGame(player1, game);
        CuT.addGame(player2, game);
        Game actual = CuT.getGame(player1);
        assertEquals(game, actual);
        actual = CuT.getGame(player2);
        assertEquals(game, actual);
    }
}
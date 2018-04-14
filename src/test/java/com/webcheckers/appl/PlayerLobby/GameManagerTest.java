package com.webcheckers.appl.PlayerLobby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for GameManager
 */
@SuppressWarnings("WeakerAccess")
@Tag("Application-tier")
public class GameManagerTest {

    private Player player1;
    private Player player2;
    private Game game1;
    private Game game2;

    private GameManager CuT;

    /**
     * Set up a new manager and players for each test
     */
    @BeforeEach
    public void setup() {
        CuT = new GameManager();

        player1 = mock(Player.class);
        String PLAYER1_NAME = "Player1 name";
        when(player1.getName()).thenReturn(PLAYER1_NAME);
        player2 = mock(Player.class);
        String PLAYER2_NAME = "Player2 name";
        when(player2.getName()).thenReturn(PLAYER2_NAME);
        game1 = mock(Game.class);
        when(game1.getRedPlayer()).thenReturn(player1);
        when(game1.getWhitePlayer()).thenReturn(player2);
        game2 = mock(Game.class);
        when(game1.getRedPlayer()).thenReturn(player1);
        when(game1.getWhitePlayer()).thenReturn(player2);
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

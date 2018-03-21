package com.webcheckers.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PlayerTest {

    private static final String NAME = "Test Name";
    private static final String NAME2 = "Testing Name";

    private Player player;

    @BeforeEach
    void setup(){
        player = new Player(NAME);
    }

    @Test
    void getName() {
        assertEquals(player.getName(), NAME);
    }

    @Test
    void equals() {
        Player player1 = new Player(NAME);
        Player player2 = new Player(NAME);
        Player player3 = new Player(NAME2);
        Game game = new Game(player1, player2);
        assertEquals(player1.equals(player2), true);
        assertEquals(player2.equals(player1), true);
        assertEquals(player3.equals(player1), false);
        assertEquals(player2.equals(player3), false);
        assertEquals(player2.equals(game), false);
    }

    @Test
    void isInGame() {
        player.setInGame(true);
        assertTrue(player.isInGame());
        player.setInGame(false);
        assertFalse(player.isInGame());
    }

    @Test
    void setInGame() {
        player.setInGame(true);
        player.setInGame(false);
    }

    @Test
    void hashCodeTest() {
        assertEquals(player.hashCode(), Objects.hash(NAME));
    }

    @Test
    void getGame() {
        Player player1 = new Player(NAME);
        Player player2 = new Player(NAME);
        Game game = new Game(player1, player2);
        player1.setGame(game);
        player2.setGame(game);
        assertEquals(player1.getGame(), game);
        assertEquals(player2.getGame(), game);
    }

    @Test
    void setGame() {
        Player player1 = new Player(NAME);
        Player player2 = new Player(NAME);
        Game game = new Game(player1, player2);
        player1.setGame(game);
        assertEquals(player1.getGame(), game);
    }

}
package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static final String NAME = "Test Name";
    private static final String NAME2 = "Testing Name";

    @Test
    void testConst() {
        Player player = new Player(NAME);
    }

    @Test
    void getName() {
        Player player = new Player(NAME);
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
        Player player = new Player(NAME);
        player.setInGame(true);
        assertEquals(player.isInGame(), true);
        player.setInGame(false);
        assertEquals(player.isInGame(), false);
    }

    @Test
    void setInGame() {
        Player player = new Player(NAME);
        player.setInGame(true);
        player.setInGame(false);
    }

    @Test
    void hashCodeTest() {
        Player player = new Player(NAME);
        assertEquals(player.hashCode(), Objects.hash(player.getName()));
    }

    @Test
    void getGame() {
        Player player1 = new Player(NAME);
        Player player2 = new Player(NAME);
        Game game = new Game(player1, player2);
        player1.setGame(game);
        player2.setGame(game);
        assertEquals(player1.getGame(),game);
        assertEquals(player2.getGame(),game);
    }

    @Test
    void setGame() {
        Player player1 = new Player(NAME);
        Player player2 = new Player(NAME);
        Game game = new Game(player1, player2);
    }

}
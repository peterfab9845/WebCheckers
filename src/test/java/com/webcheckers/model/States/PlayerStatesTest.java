package com.webcheckers.model.States;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for PlayerStates
 */
@Tag("Model-tier")
class PlayerStatesTest {

    /**
     * Test IN_LOBBY value
     */
    @Test
    void IN_LOBBY_test() {
        PlayerStates play = PlayerStates.IN_LOBBY;
        assertEquals(play, PlayerStates.valueOf("IN_LOBBY"));
    }

    /**
     * Test WON value
     */
    @Test
    void WON_test() {
        PlayerStates play = PlayerStates.WON;
        assertEquals(play, PlayerStates.valueOf("WON"));
    }

    /**
     * Test LOSS value
     */
    @Test
    void LOSS_test() {
        PlayerStates play = PlayerStates.LOSS;
        assertEquals(play, PlayerStates.valueOf("LOSS"));
    }

    /**
     * Test IN_GAME value
     */
    @Test
    void IN_GAME_test() {
        PlayerStates play = PlayerStates.IN_GAME;
        assertEquals(play, PlayerStates.valueOf("IN_GAME"));
    }
}

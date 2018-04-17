package com.webcheckers.model.States;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for ViewMode
 */
@Tag("Model-tier")
class ViewModeTest {

    /**
     * Test PLAY value
     */
    @Test
    void PLAY_test() {
        ViewMode play = ViewMode.PLAY;
        assertEquals(play, ViewMode.valueOf("PLAY"));
    }

    /**
     * Test SPECTATOR value
     */
    @Test
    void SPECTATOR_test() {
        ViewMode spectator = ViewMode.SPECTATOR;
        assertEquals(spectator, ViewMode.valueOf("SPECTATOR"));
    }

    /**
     * Test REPLAY value
     */
    @Test
    void REPLAY_test() {
        ViewMode replay = ViewMode.REPLAY;
        assertEquals(replay, ViewMode.valueOf("REPLAY"));
    }
}
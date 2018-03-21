package com.webcheckers.gameview;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class ViewModeTest {

    @Test
    void PLAY_test(){
        ViewMode play = ViewMode.PLAY;
        assertEquals(play, ViewMode.valueOf("PLAY"));
    }

    @Test
    void SPECTATOR_test(){
        ViewMode spectator = ViewMode.SPECTATOR;
        assertEquals(spectator, ViewMode.valueOf("SPECTATOR"));
    }

    @Test
    void REPLAY_test(){
        ViewMode replay = ViewMode.REPLAY;
        assertEquals(replay, ViewMode.valueOf("REPLAY"));
    }
}
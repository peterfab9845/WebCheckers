package com.webcheckers.model.states;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for MessageType
 */
@Tag("Model-tier")
class MessageTypeTest {

    /**
     * Test info value
     */
    @Test
    void info_test() {
        MessageType play = MessageType.info;
        assertEquals(play, MessageType.valueOf("info"));
    }

    /**
     * Test error value
     */
    @Test
    void error_test() {
        MessageType play = MessageType.error;
        assertEquals(play, MessageType.valueOf("error"));
    }

}

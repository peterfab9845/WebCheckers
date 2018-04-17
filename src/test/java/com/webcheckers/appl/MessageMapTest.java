package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Message;
import com.webcheckers.model.States.MessageType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Session;

/**
 * Test class for MessageMap
 */
@Tag("Application-tier")
class MessageMapTest {

    private final String MESSAGE = "Message";
    private Message message;
    private Session session;

    /**
     * Reinitialize the map, set up a mock session and a message (friendly)
     */
    @BeforeEach
    void init() {
        MessageMap.init();
        session = mock(Session.class);
        message = new Message(MESSAGE, MessageType.info);
    }

    /**
     * Test setting and getting a message for a session
     */
    @Test
    void getMessage() {
        MessageMap.setMessage(session, message);
        Message actual = MessageMap.getMessage(session);
        assertEquals(message, actual);
    }

    /**
     * Test that getting a null message returns null
     */
    @Test
    void getNullMessage() {
        Message actual = MessageMap.getMessage(session);
        assertNull(actual);
    }

    /**
     * Instantiate MessageMap because JaCoCo wants us to
     */
    @BeforeAll
    public void instantiate() {
        MessageMap map = new MessageMap(); // for coverage
    }

}
package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Message;
import com.webcheckers.model.States.MessageType;
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

    @BeforeEach
    void init() {
        MessageMap map = new MessageMap(); // for coverage
        MessageMap.init();
        session = mock(Session.class);
        message = new Message(MESSAGE, MessageType.info);
    }

    @Test
    void getMessage() {
        MessageMap.setMessage(session, message);
        Message actual = MessageMap.getMessage(session);
        assertEquals(message, actual);
    }

    @Test
    void getNullMessage() {
        Message actual = MessageMap.getMessage(session);
        assertEquals(null, actual);
    }

}
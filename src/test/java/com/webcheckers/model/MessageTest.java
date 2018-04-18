package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.webcheckers.model.states.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Message
 */
@Tag("Model-tier")
class MessageTest {

    private final String TEXT = "TESTING";
    private final String TEXT2 = "NOT TESTING";
    private final MessageType TYPE = MessageType.info;
    private final MessageType TYPE2 = MessageType.error;

    private Message CuT;

    /**
     * Set up a Message to test with
     */
    @BeforeEach
    void setup() {
        CuT = new Message(TEXT, TYPE);
    }

    /**
     * Test getting the text
     */
    @Test
    void getText() {
        String actual = CuT.getText();
        assertEquals(TEXT, actual);
    }

    /**
     * Test getting the type
     */
    @Test
    void getType() {
        MessageType actual = CuT.getType();
        assertEquals(TYPE, actual);
    }

    /**
     * Test equality
     */
    @Test
    void equals() {
        Object other = 0;
        assertNotEquals(CuT, other, "Message was equal to non-Message");

        other = new Message(TEXT2, TYPE);
        assertNotEquals(CuT, other, "Message was equal to message of different text");

        other = new Message(TEXT, TYPE2);
        assertNotEquals(CuT, other, "Message was equal to message of different type");

        other = new Message(TEXT, TYPE);
        assertEquals(CuT, other, "Message was not equal to message of same text and type");
    }

    /**
     * Test hashcodes
     */
    @Test
    void testHashCode() {
        Message other = new Message(TEXT, TYPE);
        assertEquals(CuT.hashCode(), other.hashCode(),
            "Messages of same text and type had different hashcodes");

        other = new Message(TEXT2, TYPE);
        assertNotEquals(CuT.hashCode(), other.hashCode(),
            "Message had same hashcode as message of different text");

        other = new Message(TEXT, TYPE2);
        assertNotEquals(CuT.hashCode(), other.hashCode(),
            "Message had same hashcode as message of different type");
    }
}
package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for Message
 */
@SuppressWarnings("WeakerAccess")
@Tag("Application-tier")
public class MessageTest {

    private Message CuT;

    private final String MESSAGE_TEXT_ONE = "Test message one.";
    private final String MESSAGE_TEXT_TWO = "Test message two.";

    /**
     * Test the constructor for an Error-type Message
     */
    @Test
    public void ctor_errorType() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);

        assertEquals(CuT.getText(), MESSAGE_TEXT_ONE,
            "Message constructor did not keep text.");
        assertEquals(CuT.getType(), MessageType.error,
            "Message constructor did not keep type.");
    }

    /**
     * Test the constructor for an Info-type message
     */
    @Test
    public void ctor_infoType() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.info);

        assertEquals(CuT.getText(), MESSAGE_TEXT_ONE,
            "Message constructor did not keep text.");
        assertEquals(CuT.getType(), MessageType.info,
            "Message constructor did not keep type.");
    }

    /**
     * Test that equals() is true when run on the same object
     */
    @Test
    public void equality_sameObject() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        //noinspection EqualsWithItself
        assertTrue(CuT.equals(CuT),
            "Message not equal to itself.");
        assertEquals(CuT.hashCode(), CuT.hashCode(),
            "Same message has different hashcodes.");
    }

    /**
     * Test that equals() is true with type and text the same for different Messages
     */
    @Test
    public void equality_bothSame() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        Message otherMessage = new Message(MESSAGE_TEXT_ONE, MessageType.error);

        assertTrue(CuT.equals(otherMessage),
            "Messages of same text and type not equal.");
        assertEquals(CuT.hashCode(), otherMessage.hashCode(),
            "Messages of same text and type have different hashcodes.");
    }

    /**
     * Test that equals() is false for a Message with different text and same type
     */
    @Test
    public void equality_differentText() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        Message otherMessage = new Message(MESSAGE_TEXT_TWO, MessageType.error);

        assertFalse(CuT.equals(otherMessage),
            "Messages with different text equal.");
        //not testing hashcodes being different because collisions are possible
    }

    /**
     * Test that equals() is false for a Message with different type and same text
     */
    @Test
    public void equality_differentType() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        Message otherMessage = new Message(MESSAGE_TEXT_ONE, MessageType.info);

        assertFalse(CuT.equals(otherMessage),
            "Messages with different type equal.");
        //not testing hashcodes being different because collisions are possible
    }

    /**
     * Test that equals() is false for a Message with different type and text
     */
    @Test
    public void equality_bothDifferent() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        Message otherMessage = new Message(MESSAGE_TEXT_TWO, MessageType.info);

        assertFalse(CuT.equals(otherMessage),
            "Messages of different text and type equal.");
        //not testing hashcodes being different because collisions are possible
    }

    /**
     * Test that equals() is false when passed a non-Message
     */
    @Test
    public void equality_nonMessage() {
        CuT = new Message(MESSAGE_TEXT_ONE, MessageType.error);
        Integer otherObject = 0;

        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(CuT.equals(otherObject),
            "Message equal to non-Message.");
    }
}

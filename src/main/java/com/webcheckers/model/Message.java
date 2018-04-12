package com.webcheckers.model;

import com.webcheckers.model.states.MessageType;

import java.util.Objects;

/**
 * Holds text to show to the user, and has a type to influence how text is displayed.
 */
public class Message {
    private String text;
    private MessageType type;

    /**
     * Create a Message with the given text and type.
     * @param text the text of the message
     * @param type the type of the message
     */
    public Message(String text, MessageType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Get the text stored in this Message.
     * @return this message's text
     */
    public String getText() {
        return text;
    }

    /**
     * Get which type of message this is.
     * @return the type of the message
     */
    public MessageType getType() {
        return type;
    }


    /**
     * Get the hash code for this Message.
     * Based on text and type only.
     * @return this Message's hash code
     */
    public int hashCode() {
        return Objects.hash(text, type);
    }
}

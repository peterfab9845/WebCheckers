package com.webcheckers;

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

    @Override
    public String toString() {
        return text;
    }

    /**
     * Check whether this message is the same, in type and text, as another.
     * @param obj the message with which to check equality
     * @return true if the other message has the same text and type (and is a Message)
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Message)) {
            return false;
        } else {
            Message other = (Message) obj;
            return getText().equals(other.getText()) && getType().equals(other.getType());
        }
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

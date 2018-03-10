package com.webcheckers.appl;

import java.util.Objects;

public class Message {
    private String text;
    private MessageType type;

    public Message(String text, MessageType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public MessageType getType() {
        return type;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Message)) {
            return false;
        } else {
            Message other = (Message) obj;
            return getText().equals(other.getText()) && getType().equals(other.getType());
        }
    }

    public int hashCode() {
        return Objects.hash(text, type);
    }
}

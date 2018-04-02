package com.webcheckers.appl;

import com.webcheckers.model.Message;
import spark.Session;

import java.util.HashMap;

/**
 * Manages a HashMap of sessions and message objects.
 * This way a message can be set on one page and displayed on another.
 * 
 * @author Adam Heeter
 *
 */
public class MessageMap {

    /**
     * Map to relate messages to the sessions which need to see them.
     */
    private static HashMap<String, Message> messages;

    /**
     * Initialize the map.
     */
    public static void init() {
        messages = new HashMap<>();
    }

    /**
     * Gets the message for the given session and remove it from the list.
     * @param session the session which requests a message
     * @return the message for that session, if any; otherwise null
     */
    public static Message getMessage(Session session) {
        if (messages.containsKey(session.id())) {
            // Remove the message from the list so it only gets displayed once
            return messages.remove(session.id());
        } else {
            return null;
        }
    }

    /**
     * Add a message to the list so that it can be retrieved later.
     * @param session the session which the message is for
     * @param newMessage the message for that session
     */
    public static void setMessage(Session session, Message newMessage) {
        messages.put(session.id(), newMessage);
    }
}
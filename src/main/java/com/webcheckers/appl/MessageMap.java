package com.webcheckers.appl;

import java.util.HashMap;

import com.webcheckers.model.Message;

import spark.Session;

/**
 * Manages a HashMap of sessions and message objects.
 * This way a message can be set on one page and displayed on another.
 * 
 * @author Adam Heeter
 *
 */
public class MessageMap {
    private static HashMap<String, Message> messages;
    
    public static void init() {
        messages = new HashMap<>();
    }
    
    public static Message getMessage(Session session) {
        if (messages.containsKey(session.id())) {
            // Remove the message from the list so it only gets displayed once
            Message m = messages.get(session.id());
            messages.remove(session.id());
            return m;
        } else {
            return null;
        }
    }
    
    public static void setMessage(Session session, Message newMessage) {
        messages.put(session.id(), newMessage);
    }
}
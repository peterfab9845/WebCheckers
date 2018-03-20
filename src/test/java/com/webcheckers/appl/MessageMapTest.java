package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Session;

@Tag("Application-tier")
public class MessageMapTest {
    private static final String PLAYER1_SESSIONID = "player1sessionid";
    private static final String PLAYER2_SESSIONID = "player2sessionid";
    private final String MESSAGE_TEXT_ONE = "Test message one.";
    private final String MESSAGE_TEXT_TWO = "Test message two.";
    
    private Session session1;
    private Session session2;
    
    @BeforeEach
    public void setup() {
        session1 = mock(Session.class);
        when(session1.id()).thenReturn(PLAYER1_SESSIONID);
        session2 = mock(Session.class);
        when(session2.id()).thenReturn(PLAYER2_SESSIONID);
        MessageMap.init();
    }


    @Test
    void MessageMap_test(){
        new MessageMap();
    }
    
    @Test
    public void getMessage_noPlayer() {
        assertNull(MessageMap.getMessage(session1), "getMessage did not return null for a player that does not exist.");
    }
    
    @Test
    public void getMessage_onePlayer() {
        Message testMessage = new Message(MESSAGE_TEXT_ONE, MessageType.info);
        MessageMap.setMessage(session1, testMessage);
        assertEquals(testMessage, MessageMap.getMessage(session1), "getMessage did not return the correct Message object.");
    }
    
    @Test
    public void getMessage_twoPlayers() {
        Message testMessage1 = new Message(MESSAGE_TEXT_ONE, MessageType.info);
        MessageMap.setMessage(session1, testMessage1);
        Message testMessage2 = new Message(MESSAGE_TEXT_TWO, MessageType.info);
        MessageMap.setMessage(session2, testMessage2);
        assertEquals(testMessage1, MessageMap.getMessage(session1), "getMessage did not return the correct Message object for session 1.");
        assertEquals(testMessage2, MessageMap.getMessage(session2), "getMessage did not return the correct Message object for session 2.");
    }
    
    @Test
    public void getMessage_remove() {
        Message testMessage1 = new Message(MESSAGE_TEXT_ONE, MessageType.info);
        MessageMap.setMessage(session1, testMessage1);
        MessageMap.getMessage(session1);
        assertNull(MessageMap.getMessage(session1), "getMessage did not remove the Message object for player 1.");
    }
    
    @Test
    public void setMessage_onePlayer() {
        Message testMessage1 = new Message(MESSAGE_TEXT_ONE, MessageType.info);
        MessageMap.setMessage(session1, testMessage1);
        assertEquals(testMessage1, MessageMap.getMessage(session1), "setMessage did not store the Message object.");
    }
    
    @Test
    public void setMessage_twoPlayers() {
        Message testMessage1 = new Message(MESSAGE_TEXT_ONE, MessageType.info);
        MessageMap.setMessage(session1, testMessage1);
        Message testMessage2 = new Message(MESSAGE_TEXT_TWO, MessageType.info);
        MessageMap.setMessage(session2, testMessage2);
        assertEquals(testMessage1, MessageMap.getMessage(session1), "setMessage did not store the Message object for session 1.");
        assertEquals(testMessage2, MessageMap.getMessage(session2), "setMessage did not store the Message object for session 2.");
    }
}
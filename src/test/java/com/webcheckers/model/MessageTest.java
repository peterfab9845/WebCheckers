package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.model.States.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageTest {


    private final String TEXT = "TESTINGGGG";
    private final MessageType TYPE = MessageType.info;
    private Message CuT;

    @BeforeEach
    void setup() {
        CuT = new Message(TEXT, TYPE);
    }

    @Test
    void getText() {
        String actual = CuT.getText();
        assertEquals(TEXT, actual);
    }

    @Test
    void getType() {
        MessageType actual = CuT.getType();
        assertEquals(TYPE, actual);
    }

    @Test
    void hashCode_Test() {
        CuT.hashCode();
    }
}
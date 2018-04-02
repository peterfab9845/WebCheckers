package com.webcheckers.ui.Movement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PostBackupMoveRouteTest {

    private PostBackupMoveRoute CuT;

    @BeforeEach
    void setUp() {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
//        CuT = new PostBackupMoveRoute();
    }

    @Test
    void handle() {
    }
}
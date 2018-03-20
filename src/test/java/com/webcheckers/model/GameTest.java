package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 */
public class GameTest {

    private Player red;
    private Player white;
    private Board board;
    private PieceColor whiteColor;
    private PieceColor redColor;

    private Game game;

    @BeforeEach
    public void setup(){
        red = mock(Player.class);
        white = mock(Player.class);
        board = mock(Board.class);
        whiteColor = PieceColor.WHITE;
        redColor = PieceColor.RED;
        when(new Board()).thenReturn(board);

        game = new Game(red, white);
    }

    @Test
    void getRedPlayer() {
        assertTrue(game.getRedPlayer() == red);
    }

    @Test
    void getWhitePlayer() {
        assertTrue(game.getWhitePlayer() == white);
    }

    @Test
    void getBoardView() {

    }

    @Test
    void getActiveColor() {

    }

}

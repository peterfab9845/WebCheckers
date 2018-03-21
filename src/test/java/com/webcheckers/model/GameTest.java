package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 */
@SuppressWarnings("WeakerAccess")
@Tag("Model-Tier")
public class GameTest {

    private Player red;
    private Player white;
    private Board board;
    private PieceColor redColor;
    private PieceColor whiteColor;
    private BoardView boardViewRed;
    private BoardView boardViewWhite;

    private Game game;

    @BeforeEach
    public void setup(){
        red = mock(Player.class);
        white = mock(Player.class);
        board = mock(Board.class);
        whiteColor = PieceColor.WHITE;
        redColor = PieceColor.RED;
        boardViewRed = mock(BoardView.class);
        boardViewWhite = mock(BoardView.class);
        when(new Board()).thenReturn(board);
        when(board.getBoardView(PieceColor.RED)).thenReturn(boardViewRed);
        when(board.getBoardView(PieceColor.WHITE)).thenReturn(boardViewWhite);

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
        assertTrue(game.getBoardView(red) == boardViewRed);
        assertTrue(game.getBoardView(white) == boardViewWhite);
    }

    @Test
    void getActiveColor() {
        assertTrue(game.getActiveColor() == redColor);
    }

}

package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 */
@SuppressWarnings("WeakerAccess")
@Tag("Model-Tier")
public class BoardTest {
    private Piece whitePiece;
    private Piece redPiece;
    private Piece[][] whitePieceBoard = {
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null}
    };
    private Piece[][] redPieceBoard = {
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null}
    };
    private BoardView whiteBoardView;
    private BoardView redBoardView;

    private Board board;

    @BeforeEach
    public void setup(){
        whitePiece = mock(Piece.class);
        redPiece = mock(Piece.class);
        whiteBoardView = mock(BoardView.class);
        redBoardView = mock(BoardView.class);
        when(new Piece(PieceType.SINGLE, PieceColor.WHITE)).thenReturn(whitePiece);
        when(new Piece(PieceType.SINGLE, PieceColor.RED)).thenReturn(redPiece);
        when(new BoardView(whitePieceBoard)).thenReturn(whiteBoardView);
        when(new BoardView(redPieceBoard)).thenReturn(redBoardView);

        board = new Board();
    }


    @Test
    void getBoardViewWhite() {
        assertTrue(board.getBoardView(PieceColor.WHITE) == whiteBoardView);
    }

    @Test
    void getBoardViewRed(){
        assertTrue(board.getBoardView(PieceColor.RED) == redBoardView);
    }


}

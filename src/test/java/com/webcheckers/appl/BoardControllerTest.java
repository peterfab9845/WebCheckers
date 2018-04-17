package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.board.Space;

/**
 * Test class for BoardController
 */
@Tag("Application-tier")
public class BoardControllerTest {
    
    private Space[][] spaces;
    private Board board;

    /**
     * Instantiate the class despite it being all-static because of coverage
     */
    @BeforeAll
    public static void instantiate() {
        BoardController boardController = new BoardController();
    }
    
    /**
     * Instantiate a new Space[][] for the board
     */
    @BeforeEach
    public void setup() {
        spaces = new Space[8][8];
        board = new Board();
    }
    
    /**
     * Test initializing the board's pieces
     */
    @Test
    public void initBoard() {
        LinkedList<Piece> redPieces = new LinkedList<>();
        LinkedList<Piece> whitePieces = new LinkedList<>();
        BoardController.initBoard(spaces, redPieces, whitePieces);
        assertEquals(12, redPieces.size(), "Red does not have 12 pieces");
        assertEquals(12, whitePieces.size(), "White does not have 12 pieces");
    }
    
    /**
     * Test moving a piece on the board
     */
    @Test
    public void makeMove() {
        // Moving a red piece to the other side of the board
        Position startRed = new Position(2, 1);
        Position endRed = new Position(7, 1);
        Move moveRed = new Move(startRed, endRed);
        board = new Board();
        BoardController.makeMove(board, moveRed);
        assertNull(board.valueAt(startRed), "Red piece is still in start position");
        assertNotNull(board.valueAt(endRed), "Red piece is not in end position");
        // Moving a white piece to the other side of the board
        Position startWhite = new Position(5, 0);
        Position endWhite = new Position(0, 0);
        Move moveWhite = new Move(startWhite, endWhite);
        BoardController.makeMove(board, moveWhite);
        assertNull(board.valueAt(startWhite), "White piece is still in start position");
        assertNotNull(board.valueAt(endWhite), "White piece is not in end position");
    }

}

package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.states.PieceType;
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
@SuppressWarnings("WeakerAccess")
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
    
//    /**
//     * Test moving a red piece on the board
//     */
//    @Test
//    public void makeMoveRed() {
//        Position startRed = new Position(2, 1);
//        Position endRed = new Position(0, 1);
//        Move moveRed = new Move(startRed, endRed);
//        BoardController.makeMove(board, moveRed);
//        assertNull(board.valueAt(startRed), "Red piece is still in start position");
//        assertNotNull(board.valueAt(endRed), "Red piece is not in end position");
//    }
//
//    /**
//     * Test moving a white piece on the board
//     */
//    @Test
//    public void makeMoveWhite() {
//        Position startWhite = new Position(5, 0);
//        Position endWhite = new Position(0, 0);
//        Move moveWhite = new Move(startWhite, endWhite);
//        BoardController.makeMove(board, moveWhite);
//        assertNull(board.valueAt(startWhite), "White piece is still in start position");
//        assertNotNull(board.valueAt(endWhite), "White piece is not in end position");
//    }
//
//    /**
//     * Test jumping a piece on the board
//     */
//    @Test
//    public void makeMoveJump() {
//        Position start = new Position(5, 0);
//        Position end = new Position(7, 1);
//        Position jumped = new Position(1, 0);
//        Move move = new Move(start, end);
//        move.setJumped(jumped);
//        BoardController.makeMove(board, move);
//        assertNull(board.valueAt(start), "Jumping piece is still in start position");
//        assertNotNull(board.valueAt(end), "Jumping piece is not in end position");
//        assertNull(board.valueAt(jumped), "Jumped piece was not removed");
//    }
//
//    /**
//     * Test kinging
//     */
//    @Test
//    public void makeMoveKing() {
//        Position start = new Position(2, 1);
//        Position end = new Position(7, 1);
//        Move move = new Move(start, end);
//        BoardController.makeMove(board, move);
//        assertNull(board.valueAt(start), "Piece is still in start position");
//        assertNotNull(board.valueAt(end), "Piece is not in end position");
//        assertTrue(board.isKing(end), "Piece was not kinged");
//    }
//
//    /**
//     * Test moving a null piece
//     */
//    @Test
//    public void makeMoveNull() {
//        Position startRed = new Position(0, 0);
//        Position endRed = new Position(2, 0);
//        Move moveRed = new Move(startRed, endRed);
//        BoardController.makeMove(board, moveRed);
//        assertNull(board.valueAt(startRed), "There is a piece where you moved from");
//        assertNull(board.valueAt(endRed), "There is a piece where you moved to");
//    }

    /**
     * Test getting a piece's location
     */
    @Test
    void getPieceLocation() {
        Position position = new Position(0, 1);
        Piece piece = board.valueAt(position);
        assertEquals(position, BoardController.getPieceLocation(board.getMatrix(), piece),
            "Did not get correct piece position");

        piece = new Piece(PieceType.SINGLE, PieceColor.RED);
        assertNull(BoardController.getPieceLocation(board.getMatrix(), piece),
            "Got position for piece not on board");
    }
}

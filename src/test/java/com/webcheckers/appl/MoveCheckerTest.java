package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.states.PieceColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for MoveChecker
 */
@SuppressWarnings("WeakerAccess")
@Tag("Application-tier")
public class MoveCheckerTest {

    private Board board;
    /**
     * Create a board to test on
     */
    @BeforeEach
    public void setup() {
        board = new Board(); // friendly
    }

    /**
     * Test the checking for valid moves
     */
    @Test
    public void playerHasValidMove() {
        assertTrue(MoveChecker.playerHasValidMove(board, PieceColor.RED),
            "Red had no valid moves at beginning of game");
    }

    /**
     * Test hasValidMove checking for specific position
     */
    @Test
    public void hasValidMove() {
        Position pos = new Position(5, 0);
        assertTrue(MoveChecker.hasValidMove(pos, board, PieceColor.RED),
            "Top left red piece had no valid moves at start of game");

        // test with null position
        assertFalse(MoveChecker.hasValidMove(null, board, PieceColor.RED),
            "hasValidMove returned true with null position");
    }

    /**
     * Test checking move validity
     */
    @Test
    public void isMoveValid() {
        Move move = new Move(new Position(5, 0), new Position(4, 1));
        assertTrue(MoveChecker.isMoveValid(move, board, PieceColor.RED, false, false),
            "simple diagonal move was not valid");

        // test with null move
        assertFalse(MoveChecker.isMoveValid(null, board, PieceColor.RED,
            false, false),
            "isMoveValid returned true with null position");
    }

    /**
     * Instantiate the class despite it being all-static because of coverage
     */
    @BeforeAll
    public static void instantiate() {
        MoveChecker moveChecker = new MoveChecker();
    }
}

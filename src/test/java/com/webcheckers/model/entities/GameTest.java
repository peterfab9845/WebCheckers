package com.webcheckers.model.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.BoardView;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Row;
import com.webcheckers.model.board.Space;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 *
 * Due to the nature of how the classes are built GameTest requires many other classes to be
 * friendly The required friendlies are Board, PieceColor, BoardView, Row, Space, and Piece This is
 * due to the fact that I can't intercept the creation of a board meaning the only way to compare it
 * is to use the methods and classes that we created to do that in the first place
 */
@SuppressWarnings("WeakerAccess")
@Tag("Model-tier")
public class GameTest {

    private Player red;
    private Player white;

    private Game game;

    @BeforeEach
    public void setup() {
        red = mock(Player.class);
        white = mock(Player.class);

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
    void getBoardViewWhite() {
        Board testBoard = new Board();
        BoardView testBoardBoardView = testBoard.getBoardView(PieceColor.WHITE);
        BoardView gameTestBoardView = game.getBoardView(PieceColor.WHITE);
        Iterator<Row> testBoardRows = testBoardBoardView.iterator();
        Iterator<Row> gameBoardRows = gameTestBoardView.iterator();
        while (testBoardRows.hasNext()) {
            Row testBoardRow = testBoardRows.next();
            Row gameBoardRow = gameBoardRows.next();
            Iterator<Space> testBoardSpaces = testBoardRow.iterator();
            Iterator<Space> gameBoardSpaces = gameBoardRow.iterator();
            while (testBoardSpaces.hasNext()) {
                Space testBoardSpace = testBoardSpaces.next();
                Space gameBoardSpace = gameBoardSpaces.next();
                Piece testBoardPiece = testBoardSpace.getPiece();
                Piece gameBoardPiece = gameBoardSpace.getPiece();
                if (testBoardPiece == null) {
                    assertTrue(gameBoardPiece == null);
                } else {
                    assertTrue(testBoardPiece.getColor() == gameBoardPiece.getColor()
                        && testBoardPiece.getType() == gameBoardPiece.getType());
                }
            }
        }
    }

    @Test
    void getBoardViewRed() {
        Board testBoard = new Board();
        BoardView testBoardBoardView = testBoard.getBoardView(PieceColor.RED);
        BoardView gameTestBoardView = game.getBoardView(PieceColor.RED);
        Iterator<Row> testBoardRows = testBoardBoardView.iterator();
        Iterator<Row> gameBoardRows = gameTestBoardView.iterator();
        while (testBoardRows.hasNext()) {
            Row testBoardRow = testBoardRows.next();
            Row gameBoardRow = gameBoardRows.next();
            Iterator<Space> testBoardSpaces = testBoardRow.iterator();
            Iterator<Space> gameBoardSpaces = gameBoardRow.iterator();
            while (testBoardSpaces.hasNext()) {
                Space testBoardSpace = testBoardSpaces.next();
                Space gameBoardSpace = gameBoardSpaces.next();
                Piece testBoardPiece = testBoardSpace.getPiece();
                Piece gameBoardPiece = gameBoardSpace.getPiece();
                if (testBoardPiece == null) {
                    assertTrue(gameBoardPiece == null);
                } else {
                    assertTrue(testBoardPiece.getColor() == gameBoardPiece.getColor()
                        && testBoardPiece.getType() == gameBoardPiece.getType());
                }
            }
        }
    }

    @Test
    void getActiveColor() {
        assertTrue(game.getActiveColor() == PieceColor.RED);
    }

}

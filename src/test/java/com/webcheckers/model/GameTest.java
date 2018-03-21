package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;
import com.webcheckers.gameview.Row;
import com.webcheckers.gameview.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 */
@SuppressWarnings("WeakerAccess")
@Tag("Model-tier")
public class GameTest {

    private Player red;
    private Player white;

    private Game game;

    @BeforeEach
    public void setup(){
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
        BoardView gameTestBoardView = game.getBoardView(white);
        Iterator<Row> testBoardRows = testBoardBoardView.iterator();
        Iterator<Row> gameBoardRows = gameTestBoardView.iterator();
        while(testBoardRows.hasNext()){
            Row testBoardRow = testBoardRows.next();
            Row gameBoardRow = gameBoardRows.next();
            Iterator<Space> testBoardSpaces = testBoardRow.iterator();
            Iterator<Space> gameBoardSpaces = gameBoardRow.iterator();
            while (testBoardSpaces.hasNext()){
                Space testBoardSpace = testBoardSpaces.next();
                Space gameBoardSpace = gameBoardSpaces.next();
                Piece testBoardPiece = testBoardSpace.getPiece();
                Piece gameBoardPiece = gameBoardSpace.getPiece();
                if (testBoardPiece == null){ assertTrue(gameBoardPiece == null);}
                else{
                        assertTrue(testBoardPiece.getColor() == gameBoardPiece.getColor() && testBoardPiece.getType() == gameBoardPiece.getType());
                }
            }
        }
    }

    @Test
    void getBoardViewRed(){
        Board testBoard = new Board();
        BoardView testBoardBoardView = testBoard.getBoardView(PieceColor.RED);
        BoardView gameTestBoardView = game.getBoardView(red);
        Iterator<Row> testBoardRows = testBoardBoardView.iterator();
        Iterator<Row> gameBoardRows = gameTestBoardView.iterator();
        while(testBoardRows.hasNext()){
            Row testBoardRow = testBoardRows.next();
            Row gameBoardRow = gameBoardRows.next();
            Iterator<Space> testBoardSpaces = testBoardRow.iterator();
            Iterator<Space> gameBoardSpaces = gameBoardRow.iterator();
            while (testBoardSpaces.hasNext()){
                Space testBoardSpace = testBoardSpaces.next();
                Space gameBoardSpace = gameBoardSpaces.next();
                Piece testBoardPiece = testBoardSpace.getPiece();
                Piece gameBoardPiece = gameBoardSpace.getPiece();
                if (testBoardPiece == null){ assertTrue(gameBoardPiece == null);}
                else{
                    assertTrue(testBoardPiece.getColor() == gameBoardPiece.getColor() && testBoardPiece.getType() == gameBoardPiece.getType());
                }
            }
        }
    }

    @Test
    void getActiveColor() {
        assertTrue(game.getActiveColor() == PieceColor.RED);
    }

}

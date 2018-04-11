package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;
import com.webcheckers.gameview.Row;
import com.webcheckers.gameview.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by Curtis Veronesi on 3/19/2018.
 *
 * Board test requires Piece, PieceColor, Row, Space, and BoardView to be friendly
 */
@SuppressWarnings("WeakerAccess")
@Tag("Model-tier")
public class BoardTest {


    private Board board;

    @BeforeEach
    public void setup(){

        board = new Board();
    }


    @Test
    void getBoardViewWhite() {
        BoardView test = board.getBoardView(PieceColor.WHITE);
        Iterator<Row> testIterator = test.iterator();
        Row currentRow;
        int rowIndex = -1;
        while (testIterator.hasNext()){
            rowIndex++;
            currentRow = testIterator.next();
            Iterator<Space> currentSpaceIterator = currentRow.iterator();
            while (currentSpaceIterator.hasNext()){
                Space currentSpace = currentSpaceIterator.next();
                if (rowIndex == 0 || rowIndex == 2){
                    if (currentSpace.getCellIdx()%2 == 0){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if(rowIndex == 1){
                    if (currentSpace.getCellIdx()%2 == 1){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if (rowIndex == 5 || rowIndex == 7){
                    if (currentSpace.getCellIdx()%2 == 1){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if(rowIndex == 6){
                    if (currentSpace.getCellIdx()%2 == 0){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else {
                    assertTrue(currentSpace.getPiece() == null);
                }
            }
        }
    }

    @Test
    void getBoardViewRed(){
        BoardView test = board.getBoardView(PieceColor.RED);
        Iterator<Row> testIterator = test.iterator();
        Row currentRow;
        int rowIndex = -1;
        while (testIterator.hasNext()){
            rowIndex++;
            currentRow = testIterator.next();
            Iterator<Space> currentSpaceIterator = currentRow.iterator();
            while (currentSpaceIterator.hasNext()){
                Space currentSpace = currentSpaceIterator.next();
                if (rowIndex == 0 || rowIndex == 2){
                    if (currentSpace.getCellIdx()%2 == 0){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if(rowIndex == 1){
                    if (currentSpace.getCellIdx()%2 == 1){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.WHITE && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if (rowIndex == 5 || rowIndex == 7){
                    if (currentSpace.getCellIdx()%2 == 1){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else if(rowIndex == 6){
                    if (currentSpace.getCellIdx()%2 == 0){
                        assertTrue(currentSpace.getPiece() == null);
                    }
                    else {
                        assertTrue(currentSpace.getPiece().getColor() == PieceColor.RED && currentSpace.getPiece().getType() == PieceType.SINGLE);
                    }
                }
                else {
                    assertTrue(currentSpace.getPiece() == null);
                }
            }
        }
    }


}

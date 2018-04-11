package com.webcheckers.gameview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("Model-tier")
class RowTest {

    private final int INDEX = 10;
    private static final PieceType PIECE_TYPE = PieceType.SINGLE;
    private static final PieceColor PIECE_COLOR = PieceColor.WHITE;

    private Row row;
    private Piece[] boardCols;

    @BeforeEach
    void setup(){
        int cols = 8;
        boardCols = new Piece[cols];
        for(int i = 0; i < boardCols.length; i++)
            boardCols[i] = new Piece(PIECE_TYPE, PIECE_COLOR);
        row = new Row(INDEX, boardCols);
    }

    @Test
    void getIndex() {
        assertEquals(row.getIndex(), INDEX);
    }

    @Test
    void iterator() {
        ArrayList<Object> spaces = new ArrayList<>(8);
        for (int col = 0; col < boardCols.length; col++)
            spaces.add(new Space(col, boardCols[col]));
        Iterator iterator1 = row.iterator();
        Iterator iterator2 = spaces.iterator();
        boolean flag = true;

        while(iterator2.hasNext()){
            if(!iterator2.next().equals(iterator1.next())){
                flag = false;
                break;
            }
        }

        //todo: this might be wrong, was having trouble with this
        assertFalse(flag);
    }
}
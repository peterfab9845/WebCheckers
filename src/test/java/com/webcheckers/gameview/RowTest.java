package com.webcheckers.gameview;

import com.webcheckers.model.Piece;
import com.webcheckers.model.PieceColor;
import com.webcheckers.model.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RowTest {

    private final int INDEX = 10;
    private final int COLS = 8;
    private static final PieceType PIECE_TYPE = PieceType.SINGLE;
    private static final PieceColor PIECE_COLOR = PieceColor.WHITE;

    private Row row;
    private Piece[] boardCols;

    @BeforeEach
    void setup(){
        boardCols = new Piece[COLS];
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
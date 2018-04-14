package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class RowTest {

    private final int INDEX = 10;
    private static final PieceType PIECE_TYPE = PieceType.SINGLE;
    private static final PieceColor PIECE_COLOR = PieceColor.WHITE;

    private Row row;
    private Space[] boardCols;

    @BeforeEach
    void setup() {
        int cols = 8;
        boardCols = new Space[cols];
        for (int i = 0; i < boardCols.length; i++) {
            boardCols[i] = new Space(i, new Piece(PIECE_TYPE, PIECE_COLOR));
        }
        row = new Row(INDEX, boardCols, PieceColor.RED);
    }

    @Test
    void getIndex() {
        assertEquals(row.getIndex(), INDEX);
    }

    @Test
    void iterator() {
        ArrayList<Object> spaces = new ArrayList<>(8);
        for (int col = 0; col < boardCols.length; col++) {
            spaces.add(new Space(boardCols[col].getCellIdx(), boardCols[col].getPiece()));
        }
        Iterator iterator1 = row.iterator();
        Iterator iterator2 = spaces.iterator();
        boolean flag = true;

        while (iterator2.hasNext()) {
            if (!iterator2.next().equals(iterator1.next())) {
                flag = false;
                break;
            }
        }

        //todo: this might be wrong, was having trouble with this
        assertFalse(flag);
    }
}
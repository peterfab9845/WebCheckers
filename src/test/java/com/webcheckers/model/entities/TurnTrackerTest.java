package com.webcheckers.model.entities;

import static org.mockito.Mockito.mock;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnTrackerTest {

    private TurnTracker CuT;

    @BeforeEach
    void setUp() {
        Board board = mock(Board.class);
        CuT = new TurnTracker(board);
    }

    @Test
    void finalizeTurn() {
//        Move move = mock(Move.class);
//        CuT.add(move);
//        CuT.add(move);
//        CuT.add(move);
//        CuT.finalizeTurn();
    }
}
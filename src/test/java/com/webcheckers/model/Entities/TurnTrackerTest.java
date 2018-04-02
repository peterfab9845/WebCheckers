package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TurnTrackerTest {

    private TurnTracker CuT;

    @BeforeEach
    void setUp() {
        Board board = mock(Board.class);
        CuT = new TurnTracker(board);
    }

    @Test
    void finalizeTurn() {
        Move move = mock(Move.class);
        CuT.add(move);
        CuT.add(move);
        CuT.add(move);
        CuT.finalizeTurn();
    }
}
package com.webcheckers.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MoveTest {

  private Move CuT;
  private Position start;
  private Position wrongStart;
  private Position end;
  private Position wrongEnd;

  @Test
  void equals() {
    start = mock(Position.class);
    end = mock(Position.class);
    wrongStart = mock(Position.class);
    wrongEnd = mock(Position.class);

    CuT = new Move(start, end);
    Move move = new Move(start, wrongEnd);

    boolean actual = CuT.equals(CuT);
    assertTrue(actual);
    actual = CuT.equals(start);
    assertFalse(actual);
    actual = CuT.equals(move);
    assertFalse(actual);

    move = new Move(wrongStart, end);
    actual = CuT.equals(move);
    assertFalse(actual);

  }

}
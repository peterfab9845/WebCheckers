package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewTest {

  private BoardView CuT;
  private Space[][] board = new Space[8][8];

  @BeforeEach
  void setUp() {
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        board[row][col] = new Space(col, null);
      }
    }
    CuT = new BoardView(board);
  }

  @Test
  void iterator() {
    Iterator iterator = CuT.iterator();
    while ( iterator.hasNext() )
      iterator.next();
  }
}
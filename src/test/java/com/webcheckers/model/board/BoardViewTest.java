package com.webcheckers.model.board;

import com.webcheckers.model.States.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
    CuT = new BoardView(board, PieceColor.RED);
  }

  @Test
  void iterator() {
    Iterator iterator = CuT.iterator();
    while ( iterator.hasNext() )
      iterator.next();
  }
}
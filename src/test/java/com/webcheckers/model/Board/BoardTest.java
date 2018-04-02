package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private Board CuT;

  @BeforeEach
  void setup(){
    CuT = new Board();
  }

  @Test
  void getBoardView() {
    CuT.getBoardView(PieceColor.RED);
    CuT.getBoardView(PieceColor.WHITE);
  }

  @Test
  void valueAt() {
    Position position = new Position(0, 1);
    Piece actual = CuT.valueAt(position);
    Piece expected = new Piece(PieceType.SINGLE, PieceColor.WHITE);
    assertEquals(expected, actual);
  }

  @Test
  void makeMove() {
    Position start = new Position(0, 1);
    Piece expected = CuT.valueAt(start);
    Position end = new Position(0, 1);
    CuT.makeMove(new Move(start, end));
    Piece actual = CuT.valueAt(end);
  }
}
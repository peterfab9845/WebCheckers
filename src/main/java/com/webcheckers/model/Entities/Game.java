package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.States.PieceColor;

public class Game {
  private Player redPlayer;
  private Player whitePlayer;
  private PieceColor activeColor;
  private Board board;

  public Game(Player redPlayer, Player whitePlayer){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    activeColor = PieceColor.RED;
    board = new Board();
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public PieceColor getPlayerColor(Player player) {
    if (player == redPlayer)
      return PieceColor.RED;
    else
      return PieceColor.WHITE;
  }
  /**
   * Get the BoardView from the given player's perspective
   * @return the BoardView for the given player
   */
  public BoardView getBoardView() {
    return board.getBoardView();
  }

  /**
   * Get the currently active color in this game
   * @return the active color
   */
  public PieceColor getActiveColor() {
    return activeColor;
  }

}

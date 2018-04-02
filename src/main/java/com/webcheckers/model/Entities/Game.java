package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.GameSaves.GameLog;
import com.webcheckers.model.States.PieceColor;

public class Game {
  private Player redPlayer;
  private Player whitePlayer;
  private PieceColor activeColor;
  private Board board;
  private MoveTracker moveTracker;
  private GameLog gameLog;

  public Game(Player redPlayer, Player whitePlayer){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    activeColor = PieceColor.RED;
    board = new Board();
    gameLog = new GameLog(redPlayer, whitePlayer);
    moveTracker = new MoveTracker(board);
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  /**
   * Get the BoardView from the given player's perspective
   * @return the BoardView for the given player
   */
  public BoardView getBoardView(PieceColor color) {
    return board.getBoardView(color);
  }

  /**
   * Get the currently active color in this game
   * @return the active color
   */
  public PieceColor getActiveColor() {
    return activeColor;
  }

  public void changeTurns() {
    moveTracker.finalizeTurn();
    if( activeColor == PieceColor.RED )
      activeColor = PieceColor.WHITE;
    else
      activeColor= PieceColor.RED;
  }

  public void queueMove(Move move){
    moveTracker.addMove(move);
  }
}

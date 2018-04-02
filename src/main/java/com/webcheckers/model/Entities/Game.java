package com.webcheckers.model.Entities;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.GameSaves.GameLog;
import com.webcheckers.model.States.PieceColor;

public class Game {

    /**
     * Red Player
     */
    private Player redPlayer;

    /**
     * Blue Player
     */
    private Player whitePlayer;

    /**
     * The Current state of whos turn it is turn
     */
    private PieceColor activeColor;

    /**
     * The board for the current game
     */
    private Board board;

    /**
     * The Turn Tracker to queue all moves before making them
     */
    private TurnTracker turnTracker;

    /**
     * Game log for saving all moves for the replay
     */
    private GameLog gameLog;

    public Game(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        activeColor = PieceColor.RED;
        board = new Board();
        gameLog = new GameLog(redPlayer, whitePlayer);
        turnTracker = new TurnTracker(board);
    }

    /**
     * give the red player
     * @return Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * give the white player
     * @return Player
     */
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

    /**
     * Changes the state of the game to either red players turn or whites
     */
    public void changeTurns() {
        turnTracker.finalizeTurn();
        if( activeColor == PieceColor.RED )
            activeColor = PieceColor.WHITE;
        else
            activeColor= PieceColor.RED;
    }

    /**
     * Adds a move to the turn tracker
     * @param move
     */
    public void queueMove(Move move){
        turnTracker.add(move);
    }
}

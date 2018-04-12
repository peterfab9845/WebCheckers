package com.webcheckers.model.entities;

import com.webcheckers.appl.MoveChecker;
import com.webcheckers.model.board.*;
import com.webcheckers.model.gamesaves.GameLog;
import com.webcheckers.model.states.PieceColor;

import java.util.Iterator;

public class Game implements Iterable<Move>{

    /**
     * Red Player
     */
    private PlayerEntity redPlayer;

    /**
     * Blue Player
     */
    private PlayerEntity whitePlayer;

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

    /**
     * Constructor
     * @param redPlayer
     * @param whitePlayer
     */
    public Game(PlayerEntity redPlayer, PlayerEntity whitePlayer){
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
    public PlayerEntity getRedPlayer() {
        return redPlayer;
    }

    /**
     * give the white player
     * @return Player
     */
    public PlayerEntity getWhitePlayer() {
        return whitePlayer;
    }

    /**
    * Get the BoardView from the given player's perspective
    * @return the BoardView for the given player
    */
    public BoardView getBoardView(PieceColor color) {
          return board.getBoardView(color);
    }

    public Space[][] getMatrix(){
        return board.getMatrix();
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
        MoveChecker.resetPieceMoved();
    }

    /**
     * Adds a move to the turn tracker
     * @param move
     */
    public void queueMove(Move move){
        turnTracker.add(move);
    }

    public void isGameOver(){
        int red = board.getNumRedPieces();
        int white = board.getNumWhitePieces();
        if( red == 0 ){
            redPlayer.justLost();
            whitePlayer.justWon();
        }
        else if( white == 0 ){
            redPlayer.justWon();
            whitePlayer.justLost();
        }

    }

    @Override
    public Iterator<Move> iterator() {
        return turnTracker.iterator();
    }

    public Board getBoard(){
        return board;
    }

}

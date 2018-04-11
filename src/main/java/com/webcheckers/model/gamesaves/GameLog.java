package com.webcheckers.model.gamesaves;

import com.webcheckers.model.board.Move;
import com.webcheckers.model.entities.Player;

import java.util.LinkedList;
import java.util.Queue;

public class GameLog {

    /**
     * Players that where playing in the given game
     */
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * Queue that holds every move made
     */
    private Queue<Move> moveQueue;

    /**
     * Creates an object that tracks the given game being played/ previously played
     * @param redPlayer red player in the game
     * @param whitePlayer white player in the game
     */
    public GameLog(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.moveQueue = new LinkedList<>();
    }

    /**
     * Adds the move to the queue
     * @param move move to be made to the queue
     */
    public void addMove(Move move){
        this.moveQueue.add(move);
    }

    /**
     * Takes the next move off the queue
     * @return next move that was made
     */
    public Move nextMove(){
        return this.moveQueue.remove();
    }

    /**
     * @return red player in give game
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * @return white player in give game
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

}

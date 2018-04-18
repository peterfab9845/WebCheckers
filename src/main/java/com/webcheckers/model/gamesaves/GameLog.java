package com.webcheckers.model.gamesaves;

import com.webcheckers.model.board.Move;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class GameLog {

    /**
     * Players that where playing in the given game
     */
    private PlayerEntity redPlayer;
    private PlayerEntity whitePlayer;

    /**
     * Queue that holds every move made
     */
    private Queue<Move> moveQueue;

    private Date timeStamp;

    /**
     * Creates an object that tracks the given game being played/ previously played
     * @param redPlayer red player in the game
     * @param whitePlayer white player in the game
     */
    public GameLog(PlayerEntity redPlayer, PlayerEntity whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.moveQueue = new LinkedList<>();
        timeStamp = new Date();
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
    public PlayerEntity getRedPlayer() {
        return redPlayer;
    }

    /**
     * @return white player in give game
     */
    public PlayerEntity getWhitePlayer() {
        return whitePlayer;
    }

    @Override
    public String toString() {
        return timeStamp.toString() + " - " + redPlayer.getName() + " vs " + whitePlayer.getName();
    }
}

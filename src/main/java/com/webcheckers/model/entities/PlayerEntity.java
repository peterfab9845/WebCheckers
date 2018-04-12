package com.webcheckers.model.entities;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.states.PlayerStates;
import com.webcheckers.model.board.Piece;

import java.util.LinkedList;

public class PlayerEntity {

    /**
    * The state of the object
    */
    private PlayerStates currentState;

    /**
    *Team Color - Set when game started
    */
    private PieceColor teamColor;

    /**
    * UserName For user
    */
    private String name;

    /**
     * List of pieces
     */
    private LinkedList<Piece> pieces;

    /**
     * Constructor
     * @param name
     */
    public PlayerEntity(String name){
        this.name = name;
        pieces = new LinkedList<>();
    }

    /**
    * Sets the state to IN_LOBBY
    */
    public void sendToLobby(){
        this.currentState = PlayerStates.IN_LOBBY;
    }

    /**
    * Sets the state to IN_GAME
    */
    public void setInGame(){
        this.currentState = PlayerStates.IN_GAME;
    }

    /**
     * Returns if state is IN_LOBBY
     * @return boolean
     */
    public boolean isInLobby(){
        return currentState == PlayerStates.IN_LOBBY;
    }

    /**
     * Returns if state is IN_LOBBY
     * @return boolean
     */
    public boolean isInGame(){
        return currentState == PlayerStates.IN_GAME;
    }

    /**
     * gets the players color
     * @return PieceColor
     */
    public PieceColor getTeamColor() {
        return teamColor;
    }

    /**
     * Sets the players color
     * @param teamColor
     */
    public void setTeamColor(PieceColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Returns the players name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes state of object to Won
     */
    public void justWon() {
        this.currentState = PlayerStates.WON;
    }

    /**
     * returns if state of object is Won
     */
    public boolean hasWon() {
        return this.currentState == PlayerStates.WON;
    }

    /**
     * returns if state of object is Won
     */
    public boolean hasLost() {
        return this.currentState == PlayerStates.LOSS;
    }

    /**
     * Changes state of object to Loss
     */
    public void justLost() {
        this.currentState = PlayerStates.LOSS;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

}

package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;

/**
 * Representation a game being played.
 */
public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Board board;
    private PieceColor activeColor;

    /**
     * Create a game with red and white players, and create a new Board for the game
     * @param red the red player
     * @param white the white player
     */
    public Game(Player red, Player white) {
        redPlayer = red;
        whitePlayer = white;
        board = new Board();
        activeColor = PieceColor.RED;
    }

    /**
     * Get the red player of this game
     * @return red player of this game
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Get the white player of this game
     * @return white player of this game
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }
    
    public PieceColor getPlayerColor(Player player) {
        if (player == redPlayer) {
            return PieceColor.RED;
        } else {
            return PieceColor.WHITE;
        }
    }

    /**
     * Get the BoardView from the given player's perspective
     * @param player the player whose perspective to use
     * @return the BoardView for the given player
     */
    public BoardView getBoardView(Player player) {
        if (player.equals(whitePlayer)) {
            return board.getBoardView(PieceColor.WHITE);
        } else {
            return board.getBoardView(PieceColor.RED);
        }
    }

    /**
     * Get the currently active color in this game
     * @return the active color
     */
    public PieceColor getActiveColor() {
        return activeColor;
    }
    
    public void changeActiveColor() {
        if (activeColor == PieceColor.RED) {
            activeColor = PieceColor.WHITE;
        } else {
            activeColor = PieceColor.RED;
        }
    }
    
    public Board getBoard() {
        return board;
    }
}

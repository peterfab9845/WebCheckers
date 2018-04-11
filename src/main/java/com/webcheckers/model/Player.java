package com.webcheckers.model;

import java.util.LinkedList;
import java.util.Objects;

/**
 * A player of a game of checkers
 */
public class Player {

    /**
     * The player's username
     */
    private String name;

    /**
     * Whether or not this player is in a game
     */
    private boolean inGame;

    /**
     * The game this player is in, if any
     */
    private Game game;
    private LinkedList<Move> moves;

    /**
     * Create a player with the given username.
     * Not in-game to start with
     * @param name the username of the new player
     */
    public Player(String name) {
        this.name = name;
        this.setInGame(false);
        this.game = null;
        this.moves = new LinkedList<>();
    }

    /**
     * Get the name of this player
     * @return the name of this player
     */
    public String getName() {
        return name;
    }
    
    public PieceColor getColor() {
        return game.getPlayerColor(this);
    }

    /**
     * Check whether or not this Player is equal to another Player in username
     * @param obj the player to check for equality
     * @return true if the other player has the same username
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return name.equals(((Player) obj).getName());
        }
    }

    /**
     * Get whether or not this player is in a game
     * @return true if this player is in a game
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Set whether or not this player is in a game
     * @param inGame whether or not this player is in a game
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Get the hash code of this Player
     * @return the hash code of this player
     */
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Get the game this player is in, if any
     * @return the game this player is in, if any
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Put this player in a game
     * @param newGame the game the player will be in
     */
    public void setGame(Game newGame) {
        this.game = newGame;
    }
    
    public void addMove(Move newMove) {
        moves.add(newMove);
    }
    
    public Move getLastMove() {
        if (moves.size() > 0) {
            return moves.getLast();
        } else {
            return null;
        }
    }
    
    public void removeLastMove() {
        if (moves.size() > 0) {
            moves.removeLast();
        }
    }
    
    public boolean canJumpOver(Position position) {
        Board board = game.getBoard();
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        boolean opponent = board.opponentInPosition(position, getColor());
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        return opponent;
    }
    
    public boolean canJumpTo(Position position) {
        Board board = game.getBoard();
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        boolean piece = !board.pieceInPosition(position);
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        return piece;
    }
    
    public boolean isMovingKing(Position position) {
        Board board = game.getBoard();
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        boolean king = false;
        if (moves.isEmpty()) {
            king = board.kingInPosition(position);
        } else {
            king = board.kingInPosition(moves.getFirst().getStart());
        }
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        return king;
    }
    
    public boolean makeMoves() {
        Board board = game.getBoard();
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        for (Move move : moves) {
            board.movePiece(move, getColor());
        }
        moves.clear();
        if (getColor() == PieceColor.WHITE) {
            board.prepareWhiteTurn();
        }
        return true;
    }
    
    public void endTurn() {
        game.changeActiveColor();
    }
    
    public boolean isTurn() {
        return getColor() == game.getActiveColor();
    }
}

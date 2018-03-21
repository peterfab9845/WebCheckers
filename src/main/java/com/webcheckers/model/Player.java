package com.webcheckers.model;

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

    /**
     * Create a player with the given username.
     * Not in-game to start with
     * @param name the username of the new player
     */
    public Player(String name) {
        this.name = name;
        this.setInGame(false);
        this.game = null;
    }

    /**
     * Get the name of this player
     * @return the name of this player
     */
    public String getName() {
        return name;
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
}

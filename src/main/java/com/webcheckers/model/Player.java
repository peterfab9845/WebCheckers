package com.webcheckers.model;

import java.util.Objects;

public class Player {

    private String name;
    private boolean inGame;
    private Game game;

    public Player(String name) {
        this.name = name;
        this.setInGame(false);
        this.game = null;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return name.equals(((Player) obj).getName());
        }
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int hashCode() {
        return Objects.hash(name);
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public void setGame(Game newGame) {
        this.game = newGame;
    }

}

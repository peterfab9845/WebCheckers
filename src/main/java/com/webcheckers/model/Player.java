package com.webcheckers.model;

import java.util.Objects;
import java.util.Stack;

public class Player {

    private String name;
    private boolean inGame;
    private Game game;
    private Stack<Move> moves;

    public Player(String name) {
        this.name = name;
        this.setInGame(false);
        this.game = null;
        this.moves = new Stack<>();
    }

    public String getName() {
        return name;
    }
    
    public PieceColor getColor() {
        return game.getPlayerColor(this);
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
    
    public void addMove(Move newMove) {
        moves.push(newMove);
    }
    
    public Move getLastMove() {
        if (moves.size() > 0) {
            return moves.peek();
        } else {
            return null;
        }
    }
    
    public boolean canJumpOver(Position position) {
        Board board = game.getBoard();
        return board.opponentInPosition(position, getColor());
    }
    
    public boolean canJumpTo(Position position) {
        Board board = game.getBoard();
        return !board.pieceInPosition(position);
    }
}

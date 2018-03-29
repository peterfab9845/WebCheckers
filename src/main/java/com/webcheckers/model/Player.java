package com.webcheckers.model;

import java.util.LinkedList;
import java.util.Objects;

public class Player {

    private String name;
    private boolean inGame;
    private Game game;
    private LinkedList<Move> moves;

    public Player(String name) {
        this.name = name;
        this.setInGame(false);
        this.game = null;
        this.moves = new LinkedList<>();
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

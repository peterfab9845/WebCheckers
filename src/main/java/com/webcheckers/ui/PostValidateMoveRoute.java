package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;

import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to validate the player's moves.
 *
 * @author Adam Heeter
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /validateMove} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostValidateMoveRoute(final Gson gson) {
        // validation
        Objects.requireNonNull(gson, "gson must not be null");
        //
        this.gson = gson;
        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    /**
     * Handle the player's movement request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");
        
        Player currentPlayer = PlayerLobby.getPlayer(request.session());
        String json = request.body();
        Move move = gson.fromJson(json, Move.class);
        Message responseMessage = validateMove(currentPlayer, move);
        return gson.toJson(responseMessage);
    }
    
    /**
     * Validates the player's move.
     * 
     * @param move The move the player is trying to make
     * @return Message indicating whether the move is valid
     */
    private Message validateMove(Player currentPlayer, Move move) {
        Move lastMove = currentPlayer.getLastMove();
        
        if (lastMove != null && !(move.getStart().equals(lastMove.getEnd()))) {
            return new Message("Your start does not match your last end.", MessageType.error);
        }
        
        // The player must make a jump move if one is possible
        if (isJumpMovePossible(currentPlayer, move)) {
            if (isJumpMove(currentPlayer, move)) {
                currentPlayer.addMove(move);
                return new Message("That jump move is valid.", MessageType.info);
            } else {
                return new Message("You must jump this move.", MessageType.error);
            }
        }
        
        // If no jump move is possible, the player can make a simple move
        if (isSimpleMove(currentPlayer, move)) {
            if (lastMove != null && isSimpleMove(currentPlayer, lastMove)) {
                return new Message("You can only move once this turn.", MessageType.error);
            } else {
                currentPlayer.addMove(move);
                return new Message("That move is valid.", MessageType.info);
            }
        }
        
        // Anything that isn't a simple move or a jump move
        return new Message("That move is too far away.", MessageType.error);
    }
    
    private boolean isSimpleMove(Player currentPlayer, Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        boolean movingKing = currentPlayer.isMovingKing(move.getStart());
        boolean validMove = true;
        // Moved forward one row
        if (start.getRow() != end.getRow() + 1) {
            validMove = false;
        }
        // Moved diagonally one cell
        if (start.getCell() != end.getCell() + 1 && start.getCell() != end.getCell() - 1) {
            validMove = false;
        }
        // The king is allowed to move back a row
        if (movingKing && !validMove) {
            validMove = true;
            // Moved back one row
            if (start.getRow() != end.getRow() - 1) {
                validMove = false;
            }
            // Moved back diagonally
            if (start.getCell() != end.getCell() + 1 && start.getCell() != end.getCell() - 1) {
                validMove = false;
            }
        }
        return validMove;
    }
    
    private boolean isJumpMove(Player currentPlayer, Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        boolean movingKing = currentPlayer.isMovingKing(move.getStart());
        boolean validMove = true;
        // Moved forward two rows
        if (start.getRow() != end.getRow() + 2) {
            validMove = false;
        }
        // Moved diagonally two cells
        if ((start.getCell() != end.getCell() - 2 && start.getCell() != end.getCell() + 2)) {
            validMove = false;
        }
        Position opponentPosition;
        Position endPosition;
        if (start.getCell() == end.getCell() - 2) {
            opponentPosition = new Position(start.getRow() - 1, start.getCell() + 1);
            endPosition = new Position(start.getRow() - 2, start.getCell() + 2);
        } else {
            opponentPosition = new Position(start.getRow() - 1, start.getCell() - 1);
            endPosition = new Position(start.getRow() - 2, start.getCell() - 2);
        }
        // One of the opponent's pieces is in the cell being jumped over
        if (!currentPlayer.canJumpOver(opponentPosition)) {
            validMove = false;
        }
        // No piece is in the cell being jumped to
        if (!currentPlayer.canJumpTo(endPosition)) {
            validMove = false;
        }
        // The king can jump back
        if (movingKing && !validMove) {
            // Moved back two rows
            if (start.getRow() != end.getRow() - 2) {
                validMove = false;
            }
            // Moved diagonally two cells
            if ((start.getCell() != end.getCell() - 2 && start.getCell() != end.getCell() + 2)) {
                validMove = false;
            }
            if (start.getCell() == end.getCell() - 2) {
                opponentPosition = new Position(start.getRow() + 1, start.getCell() + 1);
                endPosition = new Position(start.getRow() + 2, start.getCell() + 2);
            } else {
                opponentPosition = new Position(start.getRow() + 1, start.getCell() - 1);
                endPosition = new Position(start.getRow() + 2, start.getCell() - 2);
            }
            // One of the opponent's pieces is in the cell being jumped over
            if (!currentPlayer.canJumpOver(opponentPosition)) {
                validMove = false;
            }
            // No piece is in the cell being jumped to
            if (!currentPlayer.canJumpTo(endPosition)) {
                validMove = false;
            }
        }
        return validMove;
    }
    
    private boolean isJumpMovePossible(Player currentPlayer, Move move) {
        Position start = move.getStart();
        // Jump move to the left is possible
        Position leftJumpPosition = new Position(start.getRow() - 2, start.getCell() - 2);
        Move leftJumpMove = new Move(start, leftJumpPosition);
        if (isJumpMove(currentPlayer, leftJumpMove)) {
            return true;
        }
        // Jump move to the right is possible
        Position rightJumpPosition = new Position(start.getRow() - 2, start.getCell() + 2);
        Move rightJumpMove = new Move(start, rightJumpPosition);
        if (isJumpMove(currentPlayer, rightJumpMove)) {
            return true;
        }
        // The king can jump back left
        Position backLeftJumpPosition = new Position(start.getRow() + 2, start.getCell() - 2);
        Move backLeftJumpMove = new Move(start, backLeftJumpPosition);
        if (isJumpMove(currentPlayer, backLeftJumpMove)) {
            return true;
        }
        // The king can jump back right
        Position backRightJumpPosition = new Position(start.getRow() + 2, start.getCell() + 2);
        Move backRightJumpMove = new Move(start, backRightJumpPosition);
        if (isJumpMove(currentPlayer, backRightJumpMove)) {
            return true;
        }
        return false;
    }
}
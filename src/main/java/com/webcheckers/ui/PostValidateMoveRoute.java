package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
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

    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

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
    // TODO Allow kings to move toward the player
    // TODO Handle pieces at the edge of the board
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
        if (isSimpleMove(move)) {
            if (lastMove != null && isSimpleMove(lastMove)) {
                return new Message("You can only move once this turn.", MessageType.error);
            } else {
                currentPlayer.addMove(move);
                return new Message("That move is valid.", MessageType.info);
            }
        }
        
        // Anything that isn't a simple move or a jump move
        return new Message("That move is too far away.", MessageType.error);
    }
    
    private boolean isSimpleMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        // Moved forward one row
        if (start.getRow() != end.getRow() + 1) {
            return false;
        }
        // Moved diagonally one cell
        if ((start.getCell() != end.getCell() + 1 && start.getCell() != end.getCell() - 1)) {
            return false;
        }
        return true;
    }
    
    private boolean isJumpMove(Player currentPlayer, Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        // Moved forward two rows
        if (start.getRow() != end.getRow() + 2) {
            return false;
        }
        // Moved diagonally two cells
        if ((start.getCell() != end.getCell() - 2 && start.getCell() != end.getCell() + 2)) {
            return false;
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
            return false;
        }
        // No piece is in the cell being jumped to
        if (!currentPlayer.canJumpTo(endPosition)) {
            return false;
        }
        return true;
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
        return false;
    }
}
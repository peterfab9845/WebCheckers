package com.webcheckers.ui.Movement;

import com.google.gson.Gson;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.Message;
import com.webcheckers.appl.PlayerLobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.States.MessageType;
import com.webcheckers.ui.Game.GetGameRoute;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());


    /**
     * Gson object for transporting data
     */
    private final Gson gson;

    /**
     * Player Lobby to receive info about players in game
     */
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /validateMove} HTTP request.
     *
     * @param gson the gson instance
     */
    public PostValidateMoveRoute(final Gson gson, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
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
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the response to the AJAX action
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");

        Player currentPlayer = playerLobby.getPlayer(request.session());
        String json = request.body();
        Move move = gson.fromJson(json, Move.class);
        Game game = playerLobby.getGame(currentPlayer);
        Message responseMessage;
        boolean isKing = MoveChecker.isKing(move.getStart(), game.getMatrix());

        if( MoveChecker.isMoveValid(move, game.getMatrix(), currentPlayer.getTeamColor(),isKing) ) {
            game.queueMove(move);
            responseMessage = new Message("" , MessageType.info);
        }
        else{
            Iterator i = game.iterator();
            Move newMove;
            Position position;
            while(i.hasNext()){
                newMove = (Move)i.next();
                newMove = new Move(newMove.getStart(), move.getEnd());
                if( MoveChecker.isMoveValid(newMove, game.getMatrix(), currentPlayer.getTeamColor(), isKing )){
                    responseMessage = new Message("" , MessageType.info);
                    return gson.toJson(responseMessage);
                }
            }
            responseMessage = new Message("Invalid Move" , MessageType.error);
        }
        return gson.toJson(responseMessage);
    }
}

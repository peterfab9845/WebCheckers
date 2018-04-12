package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.ui.game.GetGameRoute;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class AI extends PlayerEntity{

    LinkedList<Piece> pieces;

    PlayerEntity enemy;

    PlayerLobby playerLobby;

    private Game game;

    /**
     * Constructor
     *
     * @param name
     */
    public AI(String name, PlayerEntity emeny, PlayerLobby playerLobby) {
        super(name);
        this.enemy = emeny;
        this.playerLobby = playerLobby;
    }

    public Game getGame(PlayerLobby playerLobby) {
        this.game  = playerLobby.getGame(enemy);
        Board board = game.getBoard();
        if( game.getRedPlayer() == this )
            pieces = board.getRedPieces();
        else
            pieces = board.getWhitePieces();
        return game;
    }

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(AI.class.getName());

    public void makeMove(Move move){
        BoardController.makeMove(game.getBoard(), move);
        game.changeTurns();
        LOG.info("move made");
    }

    public Space[][] piecesAround(Position position, int distance){
        Space[][] spaces = game.getBoard().getMatrix();
        Space[][] fieldOfView = new Space[1+(2*distance)][1+(2*distance)];
        for (int i = position.getRow() - distance; i <= position.getCell() + distance; i++){
            for (int j = position.getCell() - distance; j <= position.getCell() + distance; j++){
                fieldOfView[i][j] = spaces[position.getRow() + i][position.getCell() + j];
            }
        }
        return fieldOfView;
    }

    public void myTurn(ArtIntel ai){
        while(true) {
            Game game = playerLobby.getGame(enemy);
            if (game != null) {
                if (game.getActiveColor() == this.getTeamColor()) {
                    ai.makeDecision();
                }
                if (hasLost() || hasWon())
                    break;
            }
        }
    }
}

package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.PieceColor;
import com.webcheckers.ui.game.GetGameRoute;

import java.util.logging.Logger;

public class EasyAI extends AI implements ArtIntel {

    /**
     * Constructor
     *
     * @param name
     * @param playerLobby
     */
    public EasyAI(String name, PlayerEntity playerEntity, PlayerLobby playerLobby) {
        super(name, playerEntity, playerLobby);
        AI self = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    myTurn((ArtIntel) self);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(EasyAI.class.getName());

    @Override
    public void makeDecision() {
        Game game = getGame(playerLobby);
        int piecesNum = pieces.size();
        Space[][] board = game.getMatrix();
        if(piecesNum <= 0)
            return;

        Piece piece = pieces.get(0);
        Position position = BoardController.getPieceLocation(board, piece);
        Move move;
        int y, x;
        boolean isKing =  MoveChecker.isKing(position, board);
        PieceColor color = piece.getColor();

        int i = 0;
        while(!MoveChecker.hasValidMove(position, board, color)){
            piece = pieces.get(i);
            position = BoardController.getPieceLocation(board, piece);
            i++;
        }

        y = position.getRow();
        x = position.getCell();
        for( int row = -1; row < 2; row+=2){
            for( int col = -1; col < 2; col+=2) {
                move = new Move(position, new Position(y + row, x + col));
                if (MoveChecker.isMoveValid(move, board, color, isKing)) {
                    makeMove(move);
                }
            }
        }
    }

}

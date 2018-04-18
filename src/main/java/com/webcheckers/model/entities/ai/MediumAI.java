package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.AiPositionProtection;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.webcheckers.appl.MoveChecker.isMoveValid;

public class MediumAI extends AI implements ArtIntel {

    /**
     * Constructor
     *
     * @param name
     * @param user
     */
    public MediumAI(String name, PlayerEntity user, PlayerLobby playerLobby) {
        super(name, user, playerLobby);
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

    @Override
    public void makeDecision() {
        Board board = getGame(playerLobby).getBoard();
        ArrayList<Position> movablePositions = positionsOfMovablePieces();
        assert (movablePositions.size() > 0);

        int currentRow;
        int currentCell;
        Position testPosition;
        Move testMove;
        Move bestMove = null;
        AiPositionProtection testProtection;
        AiPositionProtection bestProtection = AiPositionProtection.UNTESTED;
        int equivalentMoves = 0;
        for (Position position : movablePositions) {
            currentRow = position.getRow();
            currentCell = position.getCell();
            if (currentRow < 7){
                if (currentCell < 7){
                    testPosition = new Position( currentRow +1, currentCell +1);
                    testMove = new Move(position, testPosition);
                    if (isMoveValid(testMove, game.getBoard(), teamColor, board.isKing(position))){
                        testProtection = analizePosition(testPosition);
                        if (testProtection.getValue() > bestProtection.getValue()){
                            bestMove = testMove;
                            bestProtection = testProtection;
                            equivalentMoves = 1;
                        }
                        else if (testProtection.getValue() == bestProtection.getValue()){
                            equivalentMoves++;
                            int random = ThreadLocalRandom.current().nextInt(0,equivalentMoves);
                            if (random == 1) bestMove = testMove;
                        }
                    }
                }
                if (currentCell > 0){
                    testPosition = new Position( currentRow +1, currentCell -1);
                    testMove = new Move(position, testPosition);
                    if (isMoveValid(testMove, game.getBoard(), teamColor, board.isKing(position))){
                        testProtection = analizePosition(testPosition);
                        if (testProtection.getValue() > bestProtection.getValue()){
                            bestMove = testMove;
                            bestProtection = testProtection;
                            equivalentMoves = 1;
                        }
                        else if (testProtection.getValue() == bestProtection.getValue()){
                            equivalentMoves++;
                            int random = ThreadLocalRandom.current().nextInt(0,equivalentMoves);
                            if (random == 1) bestMove = testMove;
                        }
                    }
                }
            }
            if (currentRow > 0){
                if (currentCell < 7){
                    testPosition = new Position( currentRow -1, currentCell +1);
                    testMove = new Move(position, testPosition);
                    if (isMoveValid(testMove, game.getBoard(), teamColor, board.isKing(position))){
                        testProtection = analizePosition(testPosition);
                        if (testProtection.getValue() > bestProtection.getValue()){
                            bestMove = testMove;
                            bestProtection = testProtection;
                            equivalentMoves = 1;
                        }
                        else if (testProtection.getValue() == bestProtection.getValue()){
                            equivalentMoves++;
                            int random = ThreadLocalRandom.current().nextInt(0,equivalentMoves);
                            if (random == 1) bestMove = testMove;
                        }
                    }
                }
                if (currentCell > 0){
                    testPosition = new Position( currentRow -1, currentCell -1);
                    testMove = new Move(position, testPosition);
                    if (isMoveValid(testMove, game.getBoard(), teamColor, board.isKing(position))){
                        testProtection = analizePosition(testPosition);
                        if (testProtection.getValue() > bestProtection.getValue()){
                            bestMove = testMove;
                            bestProtection = testProtection;
                            equivalentMoves = 1;
                        }
                        else if (testProtection.getValue() == bestProtection.getValue()){
                            equivalentMoves++;
                            int random = ThreadLocalRandom.current().nextInt(0,equivalentMoves);
                            if (random == 1) bestMove = testMove;
                        }
                    }
                }
            }
        }
        assert (bestMove != null);

        makeMove(bestMove);
    }

}

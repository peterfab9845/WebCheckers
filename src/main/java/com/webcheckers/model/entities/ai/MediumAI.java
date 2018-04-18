package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.AiPositionProtection;
import com.webcheckers.model.states.PieceColor;

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
        assert (!movablePositions.isEmpty());

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
                    if (isMoveValid(testMove, board, teamColor, board.isKing(position))){
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
                    if (isMoveValid(testMove, board, teamColor, board.isKing(position))){
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
                    if (isMoveValid(testMove, board, teamColor, board.isKing(position))){
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
                    if (isMoveValid(testMove, board, teamColor, board.isKing(position))){
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

    /**
     * Seaches for surrounding allies and enemies around a given position
     * @param position The position to be analyzed
     * @return the AiPositionProtection which informs the Ai how good the position is to move to;
     */
    public AiPositionProtection analizePosition(Position position){
        AiPositionProtection defense = AiPositionProtection.ALONE;
        int allies = 0;
        int rearAllies = 0;
        int hostiles = 0;
        int row = position.getRow();
        int cell = position.getCell();
        Board board = game.getBoard();

        //The actual checking of surrounding positions
        if (row < 7) {
            if (cell < 7){
                Position adjancentPosition = new Position(row + 1, cell + 1);
                Piece adjancentPiece = board.valueAt(adjancentPosition);
                if (adjancentPiece != null){
                    if (adjancentPiece.getColor().equals(teamColor)){
                        allies++;
                        if (teamColor.equals(PieceColor.RED)) rearAllies++;
                    }
                    else hostiles ++;
                }
            }
            if (cell > 0){
                Position adjancentPosition = new Position(row + 1, cell - 1);
                Piece adjancentPiece = board.valueAt(adjancentPosition);
                if (adjancentPiece != null){
                    if (adjancentPiece.getColor().equals(teamColor)){
                        allies++;
                        if (teamColor.equals(PieceColor.RED)) rearAllies++;
                    }
                    else hostiles ++;
                }
            }
        }
        else if (row > 0) {
            if (cell < 7){
                Position adjancentPosition = new Position(row - 1, cell + 1);
                Piece adjancentPiece = board.valueAt(adjancentPosition);
                if (adjancentPiece != null){
                    if (adjancentPiece.getColor().equals(teamColor)){
                        allies++;
                        if (teamColor.equals(PieceColor.WHITE)) rearAllies++;
                    }
                    else hostiles ++;
                }
            }
            if (cell > 0){
                Position adjancentPosition = new Position(row - 1, cell - 1);
                Piece adjancentPiece = board.valueAt(adjancentPosition);
                if (adjancentPiece != null){
                    if (adjancentPiece.getColor().equals(teamColor)){
                        allies++;
                        if (teamColor.equals(PieceColor.WHITE)) rearAllies++;
                    }
                    else hostiles ++;
                }
            }
        }

        //Assigning the proper Enum for the surrounding pieces
        if (allies > 0){
            if (allies == 1){
                if (rearAllies > 0) defense = AiPositionProtection.SINGLE_REAR_DEFENDER;
                else defense = AiPositionProtection.SINGLE_DEFENDER;
            }
            if (allies == 2){
                if (rearAllies > 1) defense = AiPositionProtection.DOUBLE_REAR_DEFENDERS;
                else defense = AiPositionProtection.DOUBLY_DEFENDED;
            }
            if (allies == 3){
                defense = AiPositionProtection.TRIPLY_DEFENDED;
            }
            if (allies == 4){
                defense = AiPositionProtection.FULL_PROTECTION;
            }
        }
        else if (hostiles > 0){
            defense = AiPositionProtection.HOSTILE;
        }

        return defense;
    }

}

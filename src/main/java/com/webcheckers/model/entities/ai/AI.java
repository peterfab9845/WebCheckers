package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.AiPositionProtection;
import com.webcheckers.model.states.PieceColor;
import com.webcheckers.ui.game.GetGameRoute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

import static com.webcheckers.appl.BoardController.getPieceLocation;
import static com.webcheckers.appl.MoveChecker.hasValidMove;

public class AI extends PlayerEntity{

    LinkedList<Piece> pieces;

    PlayerEntity enemy;

    /**
     *Team Color - Set when game started
     */
    protected PieceColor teamColor;

    PlayerLobby playerLobby;

    protected Game game;

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
     * gets the players color
     * @return PieceColor
     */
    public PieceColor getTeamColor() {
        return teamColor;
    }

    /**
     * Sets the players color
     * @param teamColor
     */
    public void setTeamColor(PieceColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Logger for logging things to the console
     */
    private static final Logger LOG = Logger.getLogger(AI.class.getName());

    public void makeMove(Move move){
        BoardController.makeMove(game.getBoard(), move);
        game.changeTurns();
        game.isGameOver();
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
                if (hasLost() || hasWon()) {
                    break;
                }
            }
        }
    }

    public Piece getRandomPiece(){
        Random rand = new Random();
        return pieces.get(rand.nextInt(pieces.size()));
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

    //Figures out what pieces can move and where they are
    public ArrayList<Position> positionsOfMovablePieces(){
        Board board = game.getBoard();
        ArrayList<Position> moveablePositions = new ArrayList<Position>(0);
        ArrayList<Position> positions = board.getLocationOfPieces(teamColor);
        for (Position position : positions) {
            if (hasValidMove(position, board.getMatrix(), teamColor))moveablePositions.add(position);
        }
        return moveablePositions;
    }

    public Move getRandomMove(){
        int x;
        int y;
        Move move;
        Piece piece;
        Position position;
        boolean isKing;
        PieceColor color;
        int piecesNum = pieces.size();
        Space[][] board = game.getMatrix();

        if(piecesNum <= 0)
            return null;

        piece = getRandomPiece();
        position = BoardController.getPieceLocation(board, piece);
        isKing =  MoveChecker.isKing(position, board);
        color = piece.getColor();

        try {
            while (!MoveChecker.hasValidMove(position, board, color)) {
                piece = getRandomPiece();
                position = BoardController.getPieceLocation(board, piece);
            }
        }
        catch (NullPointerException e) {
            pieces.remove(piece);
            LOG.info(pieces.size() + "");
            return null;
        }

        y = position.getRow();
        x = position.getCell();
        for( int row = -3; row < 4; row+=1){
            for( int col = -3; col < 4; col+=1) {
                move = new Move(position, new Position(y + row, x + col));
                if (MoveChecker.isMoveValid(move, board, color, isKing)) {
                    return move;
                }
            }
        }
        return null;
    }
}

package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.*;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.AiPositionProtection;
import com.webcheckers.model.states.PieceColor;

import java.util.*;
import java.util.logging.Logger;

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
        if(!hasWon() && !hasLost())
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

    public synchronized void myTurn(ArtIntel ai){
        while(true) {
            Game game = playerLobby.getGame(enemy);
            if (game != null) {
                if (game.getActiveColor() == this.getTeamColor()) {
                    try {
                        ai.makeDecision();
                    }
                    catch(ConcurrentModificationException e){
                        e.printStackTrace();
                    }
                }
                if (hasLost() || hasWon())
                    break;
            }
        }
    }

    public Piece getRandomPiece(Board board){
        LinkedList<Piece> currentPieces = new LinkedList<>();
        Position position;

        for (Piece piece : pieces) {
            position = BoardController.getPieceLocation(board.getMatrix(), piece);
            if (MoveChecker.hasValidMove(position, board, piece.getColor()))
                currentPieces.add(piece);
        }
        Random rand = new Random();

        if (currentPieces.isEmpty())
            return null;

        return currentPieces.get(rand.nextInt(currentPieces.size()));
    }

    //Figures out what pieces can move and where they are
    public ArrayList<Position> positionsOfMovablePieces(){
        Board board = game.getBoard();
        ArrayList<Position> moveablePositions = new ArrayList<Position>(0);
        ArrayList<Position> positions = board.getLocationOfPieces(teamColor);
        for (Position position : positions) {
            if (hasValidMove(position, game.getBoard(), teamColor))moveablePositions.add(position);
        }
        return moveablePositions;
    }

    public Move getRandomMove(){
        List<Piece> validPieces = getPiecesWithValidMoves();
        if( pieces.isEmpty() || validPieces.isEmpty() )
            return null;

        return getValidMove(validPieces);
    }

    private List<Piece> getPiecesWithValidMoves(){

        LinkedList<Piece> validPieces = new LinkedList<>();
        pieces.forEach(i ->{
            Position position = BoardController.getPieceLocation(game.getMatrix(), i);
            if(MoveChecker.hasValidMove(position, game.getBoard(), getTeamColor()))
                validPieces.add(i);
        });
        return validPieces;
    }

    private Move getValidMove(List<Piece> validPieces){
        Random random = new Random();
        LinkedList<Move> moves = new LinkedList<>();

        validPieces.forEach(piece->{
            Move move;
            Position position = BoardController.getPieceLocation(game.getMatrix(), piece);
            int x = position.getCell();
            int y = position.getRow();
            boolean isKing = MoveChecker.isKing(position, game.getMatrix());
            for( int row = -3; row < 4; row+=1){
                for( int col = -3; col < 4; col+=1) {
                    move = new Move(position, new Position(y + row, x + col));
                    if (MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(), isKing, true)) {
                        move = new Move(position, new Position(y + row, x + col));
                        moves.add(move);
                    }
                }
            }
        });

        if(moves.isEmpty())
            return null;

        return moves.get(random.nextInt(moves.size()));
    }

}

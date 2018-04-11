package com.webcheckers.model.Board;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.States.PieceType;

public class Board {

    /**
     * The 2D array of pieces in this board
     */
    private Space[][] board = new Space[8][8];

    /**
     * int representing all redPlayer Pieces
     */
    private int redPieces;

    /**
     * int representing all redPlayer Pieces
     */
    private int whitePieces;


    /**
     * Create a new board with pieces in the default starting configuration
     */
    public Board() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Space(col, null);
            }
        }

        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[2][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[6][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
            } else {
                board[1][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.WHITE));
                board[5][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
                board[7][col].setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));
            }
        }

        redPieces = 12;
        whitePieces = 12;
    }

    /**
     * Get the BoardView representation of this Board
     * @return BoardView containing the pieces from this board
     */
    public BoardView getBoardView(PieceColor color) {
        return new BoardView(board, color);
    }

    public Space[][] getMatrix(){
        return board;
    }

    /**
     * Get a 180-degree rotation of the board, for the other player's perspective
     * @return Piece array rotated 180 degrees
     */
    private Space[][] rotate180() {
        Space[][] rotated = new Space[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                rotated[7 - row][7 - col] = board[row][col];
            }
        }
        return rotated;
    }

    /**
     * Given a position it returns the peice that is at that location
     * @param position
     * @return Piece
     */
    public Piece valueAt(Position position){
        return board[position.getRow()][position.getCell()].getPiece();
    }

    /**
     * Moves whatever is at the begining to its end location
     * @param move
     */
    public void makeMove(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece piece = board[start.getRow()][start.getCell()].getPiece();
        board[start.getRow()][start.getCell()].setPiece(null);

        if(end.getRow() == 0 && piece != null && piece.getColor() == PieceColor.RED)
            piece.king();
        if(end.getRow() == 7 && piece != null && piece.getColor() == PieceColor.WHITE)
            piece.king();

        board[end.getRow()][end.getCell()].setPiece(piece);
        removePiece(move.getPieceJumped());
    }

    public void removePiece(Position position){
        if( position == null )
            return;
        Piece piece = this.valueAt(position);
        if(piece.getColor() == PieceColor.RED)
            redPieces--;
        else
            whitePieces--;
        board[position.getRow()][position.getCell()] = new Space(position.getCell(), null);
    }

    public int getRedPieces() {
        return redPieces;
    }

    public int getWhitePieces() {
        return whitePieces;
    }
}

package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;

/**
 * The server-side representation of a Checkers board
 */
public class Board {

    /**
     * The 2D array of pieces in this board
     */
    private Piece[][] board = new Piece[8][8];
    private int redPiecesRemaining;
    private int whitePiecesRemaining;

    /**
     * Create a new board with pieces in the default starting configuration
     */
    public Board() {
        //white at the top
        for (int col = 0; col < 8; col++) {
            if (col % 2 == 1) {
                board[0][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[2][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[6][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
            } else {
                board[1][col] = new Piece(PieceType.SINGLE, PieceColor.WHITE);
                board[5][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
                board[7][col] = new Piece(PieceType.SINGLE, PieceColor.RED);
            }
        }
        redPiecesRemaining = 12;
        whitePiecesRemaining = 12;
    }

    /**
     * Get the BoardView representation of this Board
     * @param perspectiveColor which player's perspective the view should be from
     * @return BoardView containing the pieces from this board
     */
    public BoardView getBoardView(PieceColor perspectiveColor) {
        if (perspectiveColor == PieceColor.WHITE) {
            return new BoardView(rotate180());
        } else {
            return new BoardView(board);
        }
    }

    /**
     * Get a 180-degree rotation of the board, for the other player's perspective
     * @return Piece array rotated 180 degrees
     */
    private Piece[][] rotate180() {
        Piece[][] rotated = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                rotated[7 - row][7 - col] = board[row][col];
            }
        }
        return rotated;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Board){
            Board otherBoard = (Board)other;
            if (getBoardView(PieceColor.WHITE).equals(otherBoard.getBoardView(PieceColor.WHITE))){
                return getBoardView(PieceColor.RED).equals(otherBoard.getBoardView(PieceColor.WHITE));
            }
        }
        return false;
    }
    
    public boolean opponentInPosition(Position position, PieceColor currentColor) {
        int row = position.getRow();
        int cell = position.getCell();
        if (row < 0 || row >= board.length || cell < 0 || cell >= board.length) {
            return true;
        }
        Piece piece = board[row][cell];
        if (piece == null) {
            return false;
        } else if (currentColor == piece.getColor()) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean pieceInPosition(Position position) {
        int row = position.getRow();
        int cell = position.getCell();
        if (row < 0 || row >= board.length || cell < 0 || cell >= board.length) {
            return true;
        }
        Piece piece = board[row][cell];
        return piece != null;
    }
    
    public boolean kingInPosition(Position position) {
        int row = position.getRow();
        int cell = position.getCell();
        if (row < 0 || row >= board.length || cell < 0 || cell >= board.length) {
            return false;
        }
        Piece piece = board[row][cell];
        if (piece != null && piece.getType() == PieceType.KING) {
            return true;
        } else {
            return false;
        }
    }
    
    public void movePiece(Move move, PieceColor currentColor) {
        Position start = move.getStart();
        Position end = move.getEnd();
        // Move the player's piece
        Piece movedPiece = board[start.getRow()][start.getCell()];
        board[start.getRow()][start.getCell()] = null;
        // If the player moves a piece to the other end of the board, it becomes a king
        if (end.getRow() == 0 && movedPiece.getType() != PieceType.KING) {
            board[end.getRow()][end.getCell()] = new Piece(PieceType.KING, currentColor);
        } else {
            board[end.getRow()][end.getCell()] = movedPiece;
        }
        // Remove an opponent piece that is jumped over
        Position opponentPosition = null;
        if (start.getRow() == end.getRow() + 2) {
            if (start.getCell() == end.getCell() - 2) {
                opponentPosition = new Position(start.getRow() - 1, start.getCell() + 1);
            } else {
                opponentPosition = new Position(start.getRow() - 1, start.getCell() - 1);
            }
        } else if (start.getRow() == end.getRow() - 2) {
            if (start.getCell() == end.getCell() - 2) {
                opponentPosition = new Position(start.getRow() + 1, start.getCell() + 1);
            } else {
                opponentPosition = new Position(start.getRow() + 1, start.getCell() - 1);
            }
        }
        if (opponentPosition != null) {
            board[opponentPosition.getRow()][opponentPosition.getCell()] = null;
            if (currentColor == PieceColor.RED) {
                whitePiecesRemaining--;
            } else {
                redPiecesRemaining--;
            }
        }
    }
    
    public void prepareWhiteTurn() {
        board = rotate180();
    }
}

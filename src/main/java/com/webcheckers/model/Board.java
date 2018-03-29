package com.webcheckers.model;

import com.webcheckers.gameview.BoardView;

/**
 * Created by Curtis Veronesi on 3/2/2018.
 */
public class Board {

    private Piece[][] board = new Piece[8][8];

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
    }

    public BoardView getBoardView(PieceColor perspectiveColor) {
        if (perspectiveColor == PieceColor.WHITE) {
            return new BoardView(rotate180());
        } else {
            return new BoardView(board);
        }
    }

    private Piece[][] rotate180() {
        Piece[][] rotated = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                rotated[7 - row][7 - col] = board[row][col];
            }
        }
        return rotated;
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
    
    public void movePiece(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        // Move the player's piece
        Piece movedPiece = board[start.getRow()][start.getCell()];
        board[start.getRow()][start.getCell()] = null;
        board[end.getRow()][end.getCell()] = movedPiece;
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
        }
    }
    
    public void prepareWhiteTurn() {
        board = rotate180();
    }
}

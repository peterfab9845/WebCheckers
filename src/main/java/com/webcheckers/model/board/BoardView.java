package com.webcheckers.model.board;

import com.webcheckers.model.states.PieceColor;

import java.util.*;

/**
 * The class representation for transmission to the client
 */
public class BoardView implements Iterable<Row> {

    /**
     * The list of Rows in the board
     */
    private List<Row> rows;

    private PieceColor color;

    /**
     * Creates a BoardView from the 2D array representation in Board
     * @param boardRows 2D array of Pieces to convert
     */
    public BoardView(Space[][] boardRows, PieceColor color) {
        this.color = color;
        rows = new ArrayList<>(8);
        for (int row = 0; row < boardRows.length; row++) {
            rows.add(new Row(row, boardRows[row], color));
        }
    }

    /**
     * Get the Iterator of the rows in this BoardView
     * @return Iterator of the rows in this BoardView
     */
    @Override
    public Iterator<Row> iterator() {
        if( color != null && color.equals(PieceColor.RED) )
            return rows.iterator();
        return reverseIterator();
    }

    private Iterator<Row> reverseIterator(){
        color = PieceColor.RED;
        Stack<Row> stack = new Stack<Row>();
        Stack<Row> reverseStack = new Stack<Row>();
        for (Object o : this)
            stack.push((Row) o);
        while(!stack.isEmpty())
            reverseStack.push(stack.pop());
        color = PieceColor.WHITE;
        return reverseStack.iterator();
    }

}

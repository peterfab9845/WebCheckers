package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.board.Space;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.PieceColor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.*;

public class HardAI extends AI implements ArtIntel {

    private HashMap<String, ArrayList<MoveMemory>> memory;
    private static final String CSV_FILE = "/Users/andrewreed/Documents/Swen/team-project-2175-swen-261-b/src/main/csv/AI1/test1.txt";
    private ArrayList<MoveMemory> currentGame;
    private static final int thoughtTime = 1000;
    private int turn;

    /**
     * Constructor
     */
    public HardAI(String name, PlayerEntity user, PlayerLobby playerLobby) {
        super(name, user, playerLobby);
        AI self = this;
        turn = 0;
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    loadMemory();
                    myTurn((ArtIntel) self);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        currentGame = new ArrayList<>();
        thread.start();
    }

    @Override
    public void makeDecision(){

        try { Thread.sleep(thoughtTime); }
        catch (InterruptedException ignored) { }

        game = getGame(playerLobby);
        String matrix = hashMatrix(game.getMatrix());

        if( memory.containsKey(matrix) ){
            out.println("Move In Memory");
        }
        else {
            Move move = getRandomMove();
            if(move == null) {
                playerLobby.removeGame(this);
                return;
            }
            boolean isKing = MoveChecker.isKing(move.getStart(), game.getMatrix());
            if( MoveChecker.isMoveValid(move, game.getMatrix(), getTeamColor(), isKing)) {
                makeMove(move);
                currentGame.add(new MoveMemory(matrix, move));
                turn++;
                out.println("move made");
            }
            else{
                out.println("move invalid");
            }
        }
    }

    private String hashMatrix(Space[][] space){
        StringBuilder value = new StringBuilder(teamColor + ",");
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(space[i][j].getPiece() == null)
                    value.append("0,");
                else if(space[i][j].getPiece().getColor() == PieceColor.RED)
                    value.append("1,");
                else if(space[i][j].getPiece().getColor() == PieceColor.WHITE)
                    value.append("2,");
                else
                    value.append("?,");
            }
        }
        return value.toString();
    }

    @Override
    public void justWon() {
        super.justWon();
        currentGame.forEach((MoveMemory i) -> {
            ArrayList<MoveMemory> mem;
            if(memory.containsKey(i.matrix)){
                mem = memory.get(i.matrix);
            }
            else {
                mem = new ArrayList<>();
                memory.put(i.matrix, mem);
            }

            try {
                writeToFile();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mem.add(i);
        });
    }

    public void loadMemory() throws IOException {
        memory = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
//                String[] info = line.split(",");
//                StringBuilder matrix = new StringBuilder("");
//                Position start = new Position(Integer.parseInt(info[65]),Integer.parseInt(info[66]));
//                Position end = new Position(Integer.parseInt(info[67]),Integer.parseInt(info[68]));
//                Move move = new Move(start, end);
//                for(int i = 1; i < 65; i++){
//                    matrix.append(info[i]);
//                }
//                MoveMemory moveMemory = new MoveMemory(matrix.toString(), move);
//                if(!memory.containsKey(matrix.toString())) {
//                    memory.put(matrix.toString(), new ArrayList<>());
//                }
//
//                memory.get(matrix.toString()).add(moveMemory);
            }
        }

    }

    private void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter(CSV_FILE, "UTF-8")) {

            for (Map.Entry<String, ArrayList<MoveMemory>> entry : memory.entrySet()) {
                String key = entry.getKey();
                ArrayList<MoveMemory> value = entry.getValue();

                value.forEach(i -> writer.println(key + i));
            }
            writer.close();
        }
    }

    @Override
    public void sendToLobby(){
        super.sendToLobby();
        MoveChecker.resetPieceMoved();
    }
}

class MoveMemory{

    public String matrix;
    public Move move;
    public int wonIn;

    public MoveMemory(String matrix, Move move){
        this.matrix = matrix;
        this.move = move;
    }

    public void setWonIn(int wonIn) {
        this.wonIn = wonIn;
    }

    @Override
    public String toString() {
        return matrix + "," + move + "," + wonIn;
    }

    public Move getMove() {
        return move;
    }
}

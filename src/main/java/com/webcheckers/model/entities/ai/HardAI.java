package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.board.Space;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.PieceColor;

import java.io.*;
import java.util.*;


public class HardAI extends AI implements ArtIntel {

    private HashMap<String, ArrayList<MoveMemory>> memory;
    private static final String CSV_FILE = "src/main/csv/AI1/test1.csv";
    private ArrayList<MoveMemory> currentGame;
    private static final long THOUGHT_PROCESS_TIME = 10;
    private static final int ACCURACY = 100;
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
    public synchronized void makeDecision(){
        try { this.wait(THOUGHT_PROCESS_TIME); }
        catch (InterruptedException ignored) { }

        game = getGame(playerLobby);
        String matrix = hashMatrix(game.getMatrix());

        if( memory.containsKey(matrix) ){
            ArrayList<MoveMemory> moveMemories = memory.get(matrix);
            Random rand = new Random();
            int n = moveMemories.size();
            int prob = rand.nextInt(ACCURACY);
            if(prob < n){
                final float avgTurns[] = {0};
                moveMemories.forEach(i -> avgTurns[0] += i.getWonIn());
                avgTurns[0] = avgTurns[0] / n;
                if(avgTurns[0] < 200.0){
                    MoveMemory min = new MoveMemory("",null);
                    min.setWonIn(201);
                    for (MoveMemory moveMemory : moveMemories) {
                        if (moveMemory.getWonIn() < min.getWonIn()) {
                            min = moveMemory;
                        }
                    }
                    Move move = min.getMove();
                    Position p = new Position(move.getStartingX(), move.getStartingY());
                    boolean isKing = MoveChecker.isKing(p,game.getMatrix());
                    if( MoveChecker.isMoveValid(move, game.getMatrix(), getTeamColor(), isKing)) {
                        makeMove(move);
                        turn++;
                        System.out.println("memory used");
                        return;
                    }
                }
            }
        }
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
        }

    }

    private String hashMatrix(Space[][] space){
        StringBuilder value = new StringBuilder("");
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
        if(hasWon() || hasLost())
            return;
        super.justWon();
        currentGame.forEach((MoveMemory i) -> {
            ArrayList<MoveMemory> mem = new ArrayList<>();
            i.setWonIn(turn);
            if(memory.containsKey(i.matrix)){
                mem = memory.get(i.matrix);
            }
            else {
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
                String[] info = line.split(",");
                StringBuilder matrix = new StringBuilder("");
                Position start = new Position(Integer.parseInt(info[64]),Integer.parseInt(info[65]));
                Position end = new Position(Integer.parseInt(info[66]),Integer.parseInt(info[67]));
                int turns = Integer.parseInt(info[68]);
                Move move = new Move(start, end);
                for(int i = 0; i < 64; i++){
                    matrix.append(info[i] + ",");
                }
                MoveMemory moveMemory = new MoveMemory(matrix.toString(), move);
                moveMemory.setWonIn(turns);
                if(!memory.containsKey(matrix.toString())) {
                    memory.put(matrix.toString(), new ArrayList<>());
                }

                memory.get(matrix.toString()).add(moveMemory);
            }
        }

    }

    private void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter(CSV_FILE, "UTF-8")) {

            for (Map.Entry<String, ArrayList<MoveMemory>> entry : memory.entrySet()) {
                ArrayList<MoveMemory> value = entry.getValue();
                value.forEach(i -> writer.println(i));
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
    public int getWonIn() {
        return wonIn;
    }

    @Override
    public String toString() {
        return matrix + move + "," + wonIn;
    }

    public Move getMove() {
        return move;
    }
}

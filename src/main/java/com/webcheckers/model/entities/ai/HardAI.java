package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.board.Space;
import com.webcheckers.model.entities.PlayerEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HardAI extends AI implements ArtIntel {

    private static HashMap<String, ArrayList<Move>> memory;
    private static final String CSV_FILE = "/Users/andrewreed/Documents/Swen/team-project-2175-swen-261-b/src/main/csv/AI1/test1.txt";
    private ArrayList<MoveMemory> currentGame;
    private int turn;

    /**
     * Constructor
     *
     * @param name
     * @param user
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
                    myTurn((ArtIntel) self);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            loadMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentGame = new ArrayList<>();
        thread.start();
    }

    @Override
    public void makeDecision() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
        turn++;
        this.game = getGame(playerLobby);
//        if(turn > 200){
//            playerLobby.removeGame(this);
//        }
        String matrix = hashMatrix(this.game.getMatrix());
        if(!memory.containsKey(matrix)){
            Move move = getRandomMove();
            if(move != null) {
                if(MoveChecker.isMoveValid(move, game.getMatrix(), teamColor, true))
                makeMove(move);
                currentGame.add(new MoveMemory(matrix, move));
            }
        }
        else{
            Random random = new Random();
            ArrayList<Move> mem = memory.get(matrix);
            int n = mem.size();
            float w = random.nextFloat();
            boolean probability = w > (1.0 / n);
            if( probability ){
                System.out.println("used remembered move");
                Move move = mem.get(0);
                //todo: add factors
                makeMove(move);
            }
            else{
                Move move = getRandomMove();
                if(move != null) {
                    makeMove(move);
                    currentGame.add(new MoveMemory(matrix, move));
                }
            }

        }
    }

    private String hashMatrix(Space[][] space){
        StringBuilder value = new StringBuilder(teamColor + ",");
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                value.append(String.valueOf(space[i][j].getPiece()));
                value.append(",");
            }
        }
        return value.toString();
    }

    @Override
    public void justWon() {
        super.justWon();
        System.out.println(getName() + " won");
        currentGame.forEach((MoveMemory i) -> {
            ArrayList<Move> mem;
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
            mem.add(i.move);
        });
    }

    private void loadMemory() throws IOException {
        memory = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                System.out.println(info.length);
                StringBuilder matrix = new StringBuilder("");
                Position start = new Position(Integer.parseInt(info[65]),Integer.parseInt(info[66]));
                Position end = new Position(Integer.parseInt(info[67]),Integer.parseInt(info[68]));
                Move move = new Move(start, end);
                for(int i = 0; i < 65; i++){
                    matrix.append(info[i]).append(",");
                    if(i < 65)
                        matrix.append(",");
                }
                MoveMemory moveMemory = new MoveMemory(matrix.toString(), move);
                if(!memory.containsKey(matrix.toString())) {
                    memory.put(matrix.toString(), new ArrayList<>());
                }

                memory.get(matrix.toString()).add(move);
            }
        }

    }

    private void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(CSV_FILE);
        file.delete();
        PrintWriter writer = new PrintWriter(CSV_FILE, "UTF-8");
        for(Map.Entry<String, ArrayList<Move>> entry : memory.entrySet()) {
            String key = entry.getKey();
            ArrayList<Move> value = entry.getValue();

            value.forEach( i -> writer.println(key + i));
        }
        writer.close();
    }
}

class MoveMemory{

    public String matrix;
    public Move move;

    public MoveMemory(String matrix, Move move){
        this.matrix = matrix;
        this.move = move;
    }

}

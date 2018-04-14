package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.MoveChecker;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.model.entities.PlayerEntity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HardAI extends AI implements ArtIntel {

    private static HashMap<String, ArrayList<Move>> memory;
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
        loadMemory();
        currentGame = new ArrayList();
        thread.start();
    }

    @Override
    public void makeDecision() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        StringBuilder value = new StringBuilder("[" + teamColor + ",");
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                value.append(String.valueOf(space[i][j].getPiece()));
                value.append(",");
            }
        }
        value.append(pieces.size()).append("]");
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

    private void loadMemory(){
        if( memory == null ) {
            memory = new HashMap<>();
        }
    }

    private void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/Users/andrewreed/Documents/Swen/team-project-2175-swen-261-b/src/main/csv/AI1/test1.txt", "UTF-8");
        for(Map.Entry<String, ArrayList<Move>> entry : memory.entrySet()) {
            String key = entry.getKey();
            ArrayList<Move> value = entry.getValue();

            value.forEach( i -> writer.println(key + "  [" + "]"));
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

package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Space;
import com.webcheckers.model.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
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
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        turn++;
        this.game = getGame(playerLobby);
        if(turn == 300){
            playerLobby.removeGame(this);
        }
        String matrix = hashMatrix(this.game.getMatrix());
        if(!memory.containsKey(matrix)){
            Move move = getRandomMove();
            if(move != null) {
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
        StringBuilder value = new StringBuilder("[");
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
        currentGame.forEach(i -> {
            ArrayList<Move> mem;
            if(memory.containsKey(i.matrix)){
                mem = memory.get(i.matrix);
            }
            else {
                mem = new ArrayList<>();
                memory.put(i.matrix, mem);
            }
            mem.add(i.move);
        });
    }

    private void loadMemory(){
        if( memory == null ) {
            memory = new HashMap<>();
        }
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

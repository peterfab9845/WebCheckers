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
    private static final long THOUGHT_PROCESS_TIME = 20;
    private static final int ACCURACY = 100;
    private static final int MAX_MOVES = 70;
    private static final int MIN_MOVES = 20;
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
                if(moveTheBetterStrat(moveMemories))
                    return;
            }
        }
        Move move = getRandomMove();
        if(move == null) {
            playerLobby.removeGame(this);
            return;
        }
        boolean isKing = MoveChecker.isKing(move.getStart(), game.getMatrix());
        if( MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(), isKing, false)) {
            makeMove(move);
            currentGame.add(new MoveMemory(matrix, move));
            turn++;
        }

    }

    private boolean moveTheBetterStrat(ArrayList<MoveMemory> moveMemories) {

        HashMap<Move, Integer> prob = new HashMap<>();
        boolean isKing;

        for (MoveMemory i : moveMemories) {
            isKing = MoveChecker.isKing(i.getMove().getStart(), game.getMatrix());
            if(MoveChecker.isMoveValid(i.getMove(), game.getBoard(), getTeamColor(), isKing, true)) {
                if (prob.containsKey(i.getMove()))
                    prob.put(i.getMove(), (prob.get(i.getMove()) + 1));
                else
                    prob.put(i.getMove(), 1);
            }
        }

        Move moves[] = new Move[prob.size()];
        final int[] index = {0};
        for(Map.Entry<Move, Integer> entry : prob.entrySet()) {
            moves[index[0]] = entry.getKey();
            index[0]++;
        };

        int turns[] = new int[prob.size()];
        int n[] = new int[prob.size()];
        int averages[] = new int[prob.size()];
        for( int i = 0; i < prob.size(); i++){
            turns[i] = 0;
            n[i] = 0;
            for (MoveMemory j : moveMemories) {
                if(j.move == moves[i]){
                    turns[i] += j.getWonIn();
                    n[i]++;
                }
            }
            averages[i] = turns[i] / n[i];
        }

        int least = 1000;
        Move move = null;
        for( int i = 0; i < averages.length; i++){
            if(averages[i] < least){
                least = averages[i];
                move = moves[i];
            }
        }

        isKing = MoveChecker.isKing(move.getStart(), game.getMatrix());
        if (MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(), isKing, false)) {
            makeMove(move);
            currentGame.add(new MoveMemory(hashMatrix(game.getMatrix()), move));
            turn++;
            System.out.println("memory used");
            return true;
        }

        return false;
    }

    private boolean minMethod(ArrayList<MoveMemory> moveMemories){
        final float avgTurns[] = {0};
        moveMemories.forEach(i -> avgTurns[0] += i.getWonIn());
        avgTurns[0] = avgTurns[0] / 1;
        if( MAX_MOVES > avgTurns[0] && avgTurns[0] > MIN_MOVES) {


            MoveMemory min = new MoveMemory("", null);
            min.setWonIn(MIN_MOVES + 1);
            for (MoveMemory moveMemory : moveMemories) {
                if (moveMemory.getWonIn() < min.getWonIn()) {
                    min = moveMemory;
                }
            }
            Move move = min.getMove();
            Position p = new Position(move.getStartingX(), move.getStartingY());
            boolean isKing = MoveChecker.isKing(p, game.getMatrix());
//            if (MoveChecker.isMoveValid(move, game.getBoard(), getTeamColor(), isKing, false)) {
//                makeMove(move);
//                turn++;
//                System.out.println("memory used");
//                return true;
//            }
        }
        return false;
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
        writeToFile();
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
                moveMemory.setHasBeenSaved(true);
                if(!memory.containsKey(matrix.toString())) {
                    memory.put(matrix.toString(), new ArrayList<>());
                }

                memory.get(matrix.toString()).add(moveMemory);
            }
        }

    }

    private void writeToFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            for (MoveMemory i : currentGame) {
                i.setWonIn(turn);
                try {
                    if (hasWon() && !i.hasBeenSaved()) {
                        bw.write(String.valueOf(i));
                        bw.newLine();
                        bw.flush();
                        i.setHasBeenSaved(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("data saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

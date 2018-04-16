package com.webcheckers.appl.playerlobby;

public class AINaming {

    private static int AI_NUM = 0;
    private static int PIECE_ID = 0;
    private static final boolean debugging = false;

    public static String getName(){
        AI_NUM++;
        return "AI" + AI_NUM;
    }

    public static int getPieceID(){
        int id = PIECE_ID;
        PIECE_ID++;
        return id;
    }

    public static boolean isDebugging() {
        return debugging;
    }
}

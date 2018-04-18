package com.webcheckers.appl.playerlobby;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.gamesaves.GameLog;
import com.webcheckers.model.states.PieceColor;

import java.util.HashMap;

public class GameManager {

    private HashMap<String, Game> games;

    public GameManager() {
        games = new HashMap<>();
    }


    /**
     * Adds a game to the game library
     */
    public void addGame(PlayerEntity player, Game game) {
        games.put(player.getName(), game);
    }

    /**
     * Given a player, returns the game that they are in
     */
    public Game getGame(PlayerEntity player) {
        return games.get(player.getName());
    }

    /**
     * Removes game from list of games, and assigns winners
     * @param player
     */
    public void removeGame(PlayerEntity player){
        Game game = getGame(player);
        GameLog gameLog = game.getGameLog();

        PlayerEntity redPlayer = game.getRedPlayer();
        if(redPlayer instanceof Player)
            ((Player)redPlayer).setLastGame(gameLog);

        PlayerEntity whitePlayer = game.getWhitePlayer();
        if(whitePlayer instanceof Player)
            ((Player)whitePlayer).setLastGame(gameLog);

        if( player == whitePlayer ){
            redPlayer.justWon();
            whitePlayer.justLost();
        }
        else{
            redPlayer.justLost();
            whitePlayer.justWon();
        }

        games.remove(game.getWhitePlayer());
        games.remove(game.getRedPlayer());
    }

    /**
     * Puts two players in a game together
     */
    public void challenge(PlayerEntity player, PlayerEntity challenging) {
        player.setInGame();
        challenging.setInGame();
        Game game = new Game(player, challenging);
        player.setTeamColor(PieceColor.RED);
        challenging.setTeamColor(PieceColor.WHITE);
        addGame(player, game);
        addGame(challenging, game);
    }

}

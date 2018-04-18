package com.webcheckers.appl.playerlobby;

import com.webcheckers.model.states.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import com.webcheckers.model.entities.PlayerEntity;
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
     */
    public void removeGame(PlayerEntity loser){
        Game game = getGame(loser);
        PlayerEntity redPlayer = game.getRedPlayer();
        PlayerEntity whitePlayer = game.getWhitePlayer();

        if( loser == whitePlayer ){
            redPlayer.justWon();
            whitePlayer.justLost();
        }
        else{
            redPlayer.justLost();
            whitePlayer.justWon();
        }

        games.remove(redPlayer.getName());
        games.remove(whitePlayer.getName());
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

package com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.States.PieceColor;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.Player;
import java.util.HashMap;

public class GameManager {

    private HashMap<String, Game> games;

    public GameManager() {
        games = new HashMap<>();
    }


    /**
     * Adds a game to the game library
     */
    public void addGame(Player player, Game game) {
        games.put(player.getName(), game);
    }

    /**
     * Given a player, returns the game that they are in
     */
    public Game getGame(Player player) {
        return games.get(player.getName());
    }

    /**
     * Removes game from list of games, and assigns winners
     */
    public void removeGame(Player loser) {
        Game game = getGame(loser);
        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();

        if (loser.equals(redPlayer)) {
            redPlayer.justLost();
            whitePlayer.justWon();
        } else {
            redPlayer.justWon();
            whitePlayer.justLost();
        }

        games.remove(redPlayer.getName());
        games.remove(whitePlayer.getName());
    }

    /**
     * Puts two players in a game together
     */
    public void challenge(Player player, Player challenging) {
        player.setInGame();
        challenging.setInGame();
        Game game = new Game(player, challenging);
        player.setTeamColor(PieceColor.RED);
        challenging.setTeamColor(PieceColor.WHITE);
        addGame(player, game);
        addGame(challenging, game);
    }

}

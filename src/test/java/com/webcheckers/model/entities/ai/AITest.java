package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.entities.Game;
import com.webcheckers.model.entities.PlayerEntity;
import com.webcheckers.model.states.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by CurtisSSD on 4/16/2018.
 */
public class AITest {

    private final String NAME = "name";
    private AI Cut;
    private PlayerEntity enemy;
    private PlayerLobby lobby;
    private Game game;
    private Board board;
    private Move validMove;

    @BeforeEach
    void setup(){
        enemy = mock(PlayerEntity.class);
        lobby = mock(PlayerLobby.class);
        Cut = new AI(NAME, enemy, lobby);
        game = mock(Game.class);
        when(lobby.getGame(enemy)).thenReturn(game);
        Cut.getGame(lobby);
        board = mock(Board.class);
        validMove = mock(Move.class);
    }

    @Test
    void getGame(){
        assertEquals(game, Cut.getGame(lobby));
    }

    @Test
    void teamColor(){
        Cut.setTeamColor(PieceColor.RED);
        assertEquals(PieceColor.RED, Cut.getTeamColor());
    }

    @Test
    void makeMove(){
        when(game.getBoard()).thenReturn(board);
        Cut.makeMove(validMove);
    }

    

}

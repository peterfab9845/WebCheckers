package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Position;
import com.webcheckers.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.entities.ai.MediumAI;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by CurtisSSD on 4/18/2018.
 */
public class MediumAITest {
    private Player enemy;
    private String name = "Name";
    private PlayerLobby lobby;
    private MediumAI CuT;
    private Board board;

    @BeforeEach
    public void setup(){
        enemy = mock(Player.class);
        lobby = mock(PlayerLobby.class);
        board = new Board();
        CuT = new MediumAI(name, enemy, lobby);
    }

    @Test
    public void makeJumpDecisionTest(){
        board.clearSpace(new Position(2,6));
        Move moveRedToJumpable = new Move(new Position(6,6), new Position(3,5));
        BoardController.makeMove(board, moveRedToJumpable);
        when(CuT.getGame(lobby).getBoard()).thenReturn(board);
        CuT.makeDecision();
        Move correctMove = new Move(new Position(2,4), new Position(4,6));
    }
}

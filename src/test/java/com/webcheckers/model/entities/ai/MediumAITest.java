package com.webcheckers.model.entities.ai;

import com.webcheckers.appl.playerlobby.PlayerLobby;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.entities.ai.AI;
import com.webcheckers.model.entities.ai.MediumAI;

import static org.mockito.Mockito.mock;
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
        board = mock(Board.class);
        CuT = new MediumAI(name, enemy, lobby);
    }

    @Test
    public void makeDecisionTest(){

    }
}

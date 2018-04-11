package com.webcheckers.model.Entities;

import com.webcheckers.model.States.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerEntityTest {

    private PlayerEntity CuT;
    private final String USERNAME = "user";


    @BeforeEach
    void setup(){
        CuT = new PlayerEntity(USERNAME);
    }

    @Test
    void sendToLobby() {
        CuT.sendToLobby();
    }

    @Test
    void setInGame() {
        CuT.setInGame();
    }

    @Test
    void isInLobby() {
        CuT.isInLobby();
    }

    @Test
    void getTeamColor() {
        CuT.getTeamColor();
    }

    @Test
    void setTeamColor() {
        CuT.setTeamColor(PieceColor.WHITE);
    }

    @Test
    void getName() {
        CuT.getName();
    }

    @Test
    void hasWon() {
        CuT.justWon();
    }

    @Test
    void hasLoss() {
        CuT.justLost();
    }
}
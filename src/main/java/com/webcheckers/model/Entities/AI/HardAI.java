package com.webcheckers.model.Entities.AI;

import com.webcheckers.model.Entities.PlayerEntity;

public class HardAI extends PlayerEntity implements ArtIntel {

    /**
     * Constructor
     *
     * @param name
     */
    public HardAI(String name) {
        super(name);
    }

    @Override
    public void makeDecision() {

    }
}

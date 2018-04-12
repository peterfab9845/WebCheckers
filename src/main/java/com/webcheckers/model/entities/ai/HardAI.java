package com.webcheckers.model.entities.ai;

import com.webcheckers.model.entities.PlayerEntity;

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

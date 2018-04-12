package com.webcheckers.model.entities.ai;

import com.webcheckers.model.entities.PlayerEntity;

public class MediumAI extends PlayerEntity implements ArtIntel {
    /**
     * Constructor
     *
     * @param name
     */
    public MediumAI(String name) {
        super(name);
    }

    @Override
    public void makeDecision() {

    }
}

package com.webcheckers.model.Entities.AI;

import com.webcheckers.model.Entities.PlayerEntity;

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

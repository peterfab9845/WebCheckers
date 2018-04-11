package com.webcheckers.model.Entities.AI;

import com.webcheckers.model.Entities.PlayerEntity;

public class EasyAI extends PlayerEntity implements ArtIntel {

    /**
     * Constructor
     *
     * @param name
     */
    public EasyAI(String name) {
        super(name);
    }

    @Override
    public void makeDecision() {

    }
}

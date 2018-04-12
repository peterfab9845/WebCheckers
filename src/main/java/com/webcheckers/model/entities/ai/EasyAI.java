package com.webcheckers.model.entities.ai;

import com.webcheckers.model.entities.PlayerEntity;

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

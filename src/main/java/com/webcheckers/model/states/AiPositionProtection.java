package com.webcheckers.model.states;

/**
 * Created by CurtisSSD on 4/12/2018.
 */
public enum  AiPositionProtection {
    UNTESTED(-2), HOSTILE(-1), ALONE(0), SINGLE_DEFENDER(1), SINGLE_REAR_DEFENDER(2), DOUBLY_DEFENDED(3), DOUBLE_REAR_DEFENDERS(4), TRIPLY_DEFENDED(5), FULL_PROTECTION(6);

    private final int value;

    AiPositionProtection(final int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}

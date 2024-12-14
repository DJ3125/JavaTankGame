package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;

abstract public class Forces {
    protected Forces(GameElementEncapsulator<?> encapsulator){
        this.encapsulator = encapsulator;
    }
    protected abstract float getAccelerationChangeForX();
    protected abstract float getAccelerationChangeForY();
    protected abstract byte priority();
    protected final GameElementEncapsulator<?> getEncapsulator(){return this.encapsulator;}
    private final GameElementEncapsulator<?> encapsulator;
}

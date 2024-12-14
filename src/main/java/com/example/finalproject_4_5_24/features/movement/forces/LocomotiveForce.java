package com.example.finalproject_4_5_24.features.movement.forces;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.movement.Forces;

public final class LocomotiveForce extends Forces {
    public LocomotiveForce(GameElementEncapsulator<?> encapsulator, float xChange, float yChange) {
        super(encapsulator);
        this.x = xChange;
        this.y = yChange;
    }

    @Override protected byte priority() {return 1;}

    @Override protected float getAccelerationChangeForX() {return this.x * this.getEncapsulator().getMass();}
    @Override protected float getAccelerationChangeForY() {return this.y * this.getEncapsulator().getMass();}

    private final float x, y;
}

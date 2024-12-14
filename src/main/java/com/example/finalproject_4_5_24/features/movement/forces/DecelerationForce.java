package com.example.finalproject_4_5_24.features.movement.forces;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.movement.Forces;

public final class DecelerationForce extends Forces {
    public DecelerationForce(GameElementEncapsulator<?> encapsulator) {super(encapsulator);}

    @Override protected float getAccelerationChangeForX() {
        float mag = this.getMagnitude();
        if(mag < 0.5){return -this.getEncapsulator().getxVel() * this.getEncapsulator().getMass();}
        else{return (float) (this.multiplier * Math.cos(this.getAngle() + Math.PI) * mag * this.getEncapsulator().getMass());}
    }

    @Override protected byte priority() {return 10;}

    @Override protected float getAccelerationChangeForY() {
        float mag = this.getMagnitude();
        if(mag < 0.5){return -this.getEncapsulator().getyVel() * this.getEncapsulator().getMass();}
        else{return (float) (this.multiplier * Math.sin(this.getAngle() + Math.PI) * mag * this.getEncapsulator().getMass());}
    }

    private float getAngle(){
        if(this.getEncapsulator().getxVel() != 0 || this.getEncapsulator().getyVel() != 0){return (float) AnglesAndTrig.getAngle(this.getEncapsulator().getxVel(), this.getEncapsulator().getyVel());}
        return 0;
    }
    private float getMagnitude(){return (float) AnglesAndTrig.findDist(this.getEncapsulator().getxVel(), this.getEncapsulator().getyVel());}

    private final float multiplier = 0.1f;
}

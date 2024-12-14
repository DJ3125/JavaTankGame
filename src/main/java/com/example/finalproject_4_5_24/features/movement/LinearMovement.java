package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.features.movement.forces.DecelerationForce;
import com.example.finalproject_4_5_24.features.movement.forces.LocomotiveForce;

final class LinearMovement extends Movement{
    LinearMovement(DynamicGameElement.FeatureInitializationInfo info, float x, float y, float angle, float acceleration){
        super(info, x, y, angle);
        this.velocity = acceleration;
        this.addForce(new LocomotiveForce(this.getEncapsulator(), (float)(this.velocity * Math.cos(angle)), (float)(this.velocity * Math.sin(angle))));
        this.getLocationAndVelocity().update();
    }

    @Override public void move() {
        if(this.getxVel() != 0 || this.getyVel() != 0){
            float movementAngle = (float) AnglesAndTrig.getAngle(this.getxVel(), this.getyVel());
            this.addForce(new LocomotiveForce(this.getEncapsulator(), (float)(this.velocity * Math.cos(movementAngle)), (float)(this.velocity * Math.sin(movementAngle))));
            this.addForce(new DecelerationForce(this.getEncapsulator()));
            this.getLocationAndVelocity().update();
            this.getNotifier().notifyOfChange();
        }
    }

    private final float velocity;

}

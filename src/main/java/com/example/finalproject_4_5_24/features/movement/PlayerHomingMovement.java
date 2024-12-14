package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.Player;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.movement.forces.DecelerationForce;
import com.example.finalproject_4_5_24.features.movement.forces.LocomotiveForce;

import java.util.ArrayList;

final class PlayerHomingMovement extends Movement{
    PlayerHomingMovement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {
        super(info, xPosition, yPosition, angle);
        this.acceleration = acceleration;
    }
    @Override public void move() {
        ArrayList<GameElementEncapsulator<? extends Player>> encapsulator = this.getEncapsulator().gameEnvironment().operator().getAllEntitiesThatSatisfyCondition(Player.class, (encapsulation) -> true);
        if(!encapsulator.isEmpty()){
            float[] myCenter = this.getEncapsulator().getDimensionsEncapsulator().getAdjustedCenter(), otherCenter = encapsulator.get(0).getDimensionsEncapsulator().getAdjustedCenter();
//            float x = this.acceleration, y = this.acceleration;
//            if(otherCenter[0] < myCenter[0]){x *= -1;}
//            if(otherCenter[1] < myCenter[1]){y *= -1;}
            double angle = Math.PI + AnglesAndTrig.getAngle(myCenter[0], myCenter[1], otherCenter[0], otherCenter[1]);
            this.addForce(new LocomotiveForce(this.getEncapsulator(), (float) (this.acceleration * Math.cos(angle)), (float)(this.acceleration * Math.sin(angle))));
        }
        this.addForce(new DecelerationForce(this.getEncapsulator()));
        this.getLocationAndVelocity().update();
        this.getNotifier().notifyOfChange();
    }

    private final float acceleration;
}

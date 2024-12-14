package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.features.movement.forces.DecelerationForce;

final class InteractMovement extends Movement{
    InteractMovement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition) {super(info, xPosition, yPosition);}

    @Override public void move() {
        this.addForce(new DecelerationForce(this.getEncapsulator()));
        this.getLocationAndVelocity().update();
        this.getNotifier().notifyOfChange();
    }
}

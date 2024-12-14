package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

public sealed interface Velocities extends Located, Angled permits GameElementEncapsulator, DynamicGameElement, LocationAndVelocity, Movement, MovementTypes.MovementEncapsulator {
    float getAngAccel();
    float getAngVel();
    float getxAccel();
    float getyAccel();
    float getxVel();
    float getyVel();
}

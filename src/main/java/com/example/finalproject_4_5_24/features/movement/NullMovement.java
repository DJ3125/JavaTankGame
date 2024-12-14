package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class NullMovement extends Movement{
    NullMovement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle) {super(info, xPosition, yPosition, angle);}
    @Override public void move() {}
}

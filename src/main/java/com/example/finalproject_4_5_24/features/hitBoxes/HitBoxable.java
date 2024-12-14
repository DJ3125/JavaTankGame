package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

import java.util.function.BiPredicate;

public sealed interface HitBoxable permits GameElementEncapsulator, DynamicGameElement, HitBoxTypes.HitBoxEncapsulator, HitBoxes {
    boolean contains(float x, float y);
    boolean isCollided(GameElementEncapsulator<?> encapsulator);
    boolean checkIfPointsAreInsideOther(BiPredicate<Float, Float> contains);
    float[][] getThisVertices();
    boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator);
}

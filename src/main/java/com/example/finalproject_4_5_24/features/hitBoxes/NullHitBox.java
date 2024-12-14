package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;

import java.util.function.Predicate;

final class NullHitBox extends HitBoxes{
    NullHitBox(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser) {super(info, chooser);}
    @Override protected boolean isInside(float x, float y) {return false;}
    @Override protected float[][] getVertices() {return new float[0][];}
}

package com.example.finalproject_4_5_24.features.scalingAndPositioning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class StaticPositioning extends ScalingAndPositioning{
    StaticPositioning(DynamicGameElement.FeatureInitializationInfo info, float xDist, float yDist, float width, float height, float xScale, float yScale){super(info, xDist, yDist, width, height, xScale, yScale);}
    @Override public void updateScaling() {}
}

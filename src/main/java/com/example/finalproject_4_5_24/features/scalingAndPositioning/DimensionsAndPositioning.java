package com.example.finalproject_4_5_24.features.scalingAndPositioning;

sealed interface DimensionsAndPositioning permits PositioningTypes.DimensionsEncapsulator, ScalingAndPositioning {
    float getYDistanceFromCenter();
    float getXDistanceFromCenter();
    float getXScale();
    float getYScale();
    float getHeight();
    float getWidth();
    float[] getAdjustedCenter();
}

package com.example.finalproject_4_5_24.features.scalingAndPositioning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;

public enum PositioningTypes {
    STATIC{@Override public final DimensionsEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xDist, float yDist, float width, float height, float xScale, float yScale) {return this.encapsulate(new StaticPositioning(info, xDist, yDist, width, height, xScale, yScale));}};
    public abstract DimensionsEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xDist, float yDist, float width, float height, float xScale, float yScale);
    protected final DimensionsEncapsulator encapsulate(ScalingAndPositioning scalingAndPositioning){return new DimensionsEncapsulator(scalingAndPositioning);}

    public static final class DimensionsEncapsulator extends FeatureEncapsulator<ScalingAndPositioning> implements DimensionsAndPositioning {
        private DimensionsEncapsulator(ScalingAndPositioning dimensions){super(ScalingAndPositioning.class, dimensions);}
        @Override public float[] getAdjustedCenter(){return this.getFeature().getAdjustedCenter();}
        @Override public float getYDistanceFromCenter() {return this.getFeature().getYDistanceFromCenter();}
        @Override public float getXDistanceFromCenter() {return this.getFeature().getXDistanceFromCenter();}
        @Override public float getXScale() {return this.getFeature().getXScale();}
        @Override public float getYScale() {return this.getFeature().getYScale();}
        @Override public float getHeight() {return this.getFeature().getHeight();}
        @Override public float getWidth() {return this.getFeature().getWidth();}
    }
}

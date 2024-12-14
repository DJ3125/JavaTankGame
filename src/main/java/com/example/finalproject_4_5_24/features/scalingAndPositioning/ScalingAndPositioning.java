package com.example.finalproject_4_5_24.features.scalingAndPositioning;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;

abstract sealed public class ScalingAndPositioning extends Features implements DimensionsAndPositioning permits StaticPositioning{
    protected ScalingAndPositioning(DynamicGameElement.FeatureInitializationInfo info, float xDist, float yDist, float width, float height, float xScale, float yScale){
        super(info);
        this.xDist = xDist;
        this.yDist = yDist;
        this.xScale = xScale;
        this.yScale = yScale;
        this.width = width;
        this.height = height;
        this.adjustedCenter = this.updateAdjustedCenter();
    }


    @Override public final float[] getAdjustedCenter() {return this.adjustedCenter.clone();}

    private float[] updateAdjustedCenter(){
        double cenX = this.xDist * this.xScale, cenY = this.yDist * this.yScale, angle = AnglesAndTrig.getAngle(cenX, cenY) + this.getEncapsulator().getAngle(), dist = AnglesAndTrig.findDist(cenX, cenY);
        return new float[]{(float)Math.round(Math.cos(angle) * dist) + this.getEncapsulator().getX(), (float)Math.round(Math.sin(angle) * dist) + this.getEncapsulator().getY()};
    }

    @Override protected final void updateOnTurn() {}

    @Override protected void updateOnChange() {this.adjustedCenter = this.updateAdjustedCenter();}

    @Override
    protected void updateOnTick() {

    }

    abstract public void updateScaling();
    @Override public final float getYDistanceFromCenter() {return this.yDist;}
    @Override public final float getXDistanceFromCenter() {return this.xDist;}
    @Override public final float getXScale() {return this.xScale;}
    @Override public final float getYScale() {return this.yScale;}
    @Override public final float getHeight() {return this.height;}
    @Override public final float getWidth() {return this.width;}
    protected final void setXScale(float value){this.xScale = value;}
    protected final void setYScale(float value){this.yScale = value;}

    private final float xDist, yDist, width, height;
    private float xScale, yScale;
    private float[] adjustedCenter;
}

package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

import java.util.function.Predicate;

final class EllipticalHitBox extends HitBoxes{
    EllipticalHitBox(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser){super(info, chooser);}
    @Override protected boolean isInside(float x, float y) {
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();
        float[] point = this.getRelativePosition(x, y);
        float relX = point[0], relY = point[1];
        return Math.pow(relX, 2)/Math.pow(dimensions.getWidth()/2.0, 2) + Math.pow(relY, 2)/Math.pow(dimensions.getHeight()/2.0, 2) <= 1;
    }

    @Override protected float[][] getVertices() {
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();
        short pointAmount = 16;
        float[][] vertices = new float[pointAmount][];
        for(short i = 0; i < pointAmount; i++){
            double time = Math.TAU/pointAmount * i;
            vertices[i] = new float[]{dimensions.getWidth()/2 * (float)Math.cos(time), dimensions.getHeight()};
        }
        return vertices;
    }
}

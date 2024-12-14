package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

import java.util.function.Predicate;

final class RectangleHitBox extends HitBoxes{
    RectangleHitBox(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser){super(info, chooser);}

    @Override protected boolean isInside(float x, float y) {
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();
        float[] point = this.getRelativePosition(x, y);
        float relX = point[0], relY = point[1];
        return Math.abs(relX) <= dimensions.getWidth()/2 && Math.abs(relY) <= dimensions.getHeight()/2;
    }

    @Override protected float[][] getVertices() {
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();

        byte verticesCount = 4;
        float[][] vertices = new float[verticesCount][];
        for(byte i = 0; i < 4; i++){
            double angle = Math.PI/2 * i + Math.PI/4;
            float xMult = (float) Math.round(Math.sqrt(2) * Math.cos(angle)), yMult = (float) Math.round(Math.sqrt(2) * Math.sin(angle));
            vertices[i * verticesCount/4] = new float[]{dimensions.getWidth()/2 * xMult, dimensions.getHeight()/2 * yMult};
        }

        return vertices;
    }
}

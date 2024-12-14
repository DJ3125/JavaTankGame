package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public enum HitBoxTypes {
    RECTANGLE{@Override public final HitBoxEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser){return this.encapsulator(new RectangleHitBox(info, chooser));}},
    NULL{@Override public final HitBoxEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser) {return this.encapsulator(new NullHitBox(info, chooser));}},
    ELLIPSE{@Override public final HitBoxEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser){return this.encapsulator(new EllipticalHitBox(info, chooser));}};
    abstract public HitBoxEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser);
    protected final HitBoxEncapsulator encapsulator(HitBoxes hitBoxes){return new HitBoxEncapsulator(hitBoxes);}

    public static final class HitBoxEncapsulator extends FeatureEncapsulator<HitBoxes> implements HitBoxable{
        private HitBoxEncapsulator(HitBoxes hitBoxes){super(HitBoxes.class, hitBoxes);}
        @Override public boolean contains(float x, float y) {return this.getFeature().contains(x, y);}
        @Override public boolean checkIfPointsAreInsideOther(BiPredicate<Float, Float> contains) {return this.getFeature().checkIfPointsAreInsideOther(contains);}
        @Override public boolean isCollided(GameElementEncapsulator<?> encapsulator) {return this.getFeature().isCollided(encapsulator);}
        @Override public float[][] getThisVertices() {return this.getFeature().getThisVertices();}
        void notifyForCollision(HitBoxes.ModifiedCollisionNotification notification){this.getFeature().notifyForCollision(notification);}

//        public Located getMagnitudes(float x, float y){
//            return this.getFeature().getMagnitude(x, y);
//        }

        @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return this.getFeature().isPotentialForCollision(encapsulator);}
    }
}

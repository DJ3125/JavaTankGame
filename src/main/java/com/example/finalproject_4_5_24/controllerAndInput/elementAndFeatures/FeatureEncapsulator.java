package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

abstract sealed public class FeatureEncapsulator<T extends Features> permits AttackTypes.AttackEncapsulator, CollectingTypes.CollectingEncapsulator, HealthTypes.HealthEncapsulator, HitBoxTypes.HitBoxEncapsulator, MovementTypes.MovementEncapsulator, PositioningTypes.DimensionsEncapsulator, SpawningTypes.SpawnerEncapsulator {
    protected FeatureEncapsulator(Class<? super T> classObject, T feature){
        if(classObject == Features.class){throw new RuntimeException("Invalid Class");}
        this.classObject = classObject;
        this.feature = feature;
    }
    protected final T getFeature(){return this.feature;}
    protected final Class<? super T> getClassObject() {return this.classObject;}
    private final T feature;
    private final Class<? super T> classObject;
}

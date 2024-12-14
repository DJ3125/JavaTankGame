package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.features.attack.Attack;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.Collecting;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.health.Health;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.Movement;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.ScalingAndPositioning;
import com.example.finalproject_4_5_24.features.spawning.Spawner;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

import java.util.HashMap;
import java.util.function.Consumer;

final class FeatureHolder implements HoldingFeature{
    FeatureHolder(){for(Class<? extends Features> aClass : mapping.keySet()) {this.pairs[mapping.get(aClass)] = new Pair<>(aClass);}}

    void initializeFeatureHolder(DynamicGameElement.FeatureInitializationInfo initializationInfo){
        this.setFeatureEncapsulator(MovementTypes.NULL_MOVEMENT.getEncapsulator(initializationInfo, 0, 0, 0, 0));
        this.setFeatureEncapsulator(PositioningTypes.STATIC.getEncapsulator(initializationInfo, 0, 0, 0, 0, 0, 0));
        this.setFeatureEncapsulator(HitBoxTypes.NULL.getEncapsulator(initializationInfo, encapsulator -> false));
        this.setFeatureEncapsulator(HealthTypes.NULL_HEALTH.getEncapsulator(initializationInfo, 0));
        this.setFeatureEncapsulator(AttackTypes.NULL_ATTACK.getEncapsulator(initializationInfo, 0));
        this.setFeatureEncapsulator(SpawningTypes.NULL_SPAWNER.getEncapsulator(initializationInfo, 0));
        this.setFeatureEncapsulator(CollectingTypes.NULL_COLLECTING.getEncapsulator(initializationInfo, 0));
        for(Pair<?> i : this.pairs){if(i.encapsulator == null){throw new RuntimeException("Not all features have been initialized");}}
    }

    @SuppressWarnings("unchecked") private <T extends Features> FeatureEncapsulator<T> getFeature(Class<T> tClass){
        FeatureEncapsulator<T> encapsulator = (FeatureEncapsulator<T>) this.pairs[mapping.get(tClass)].encapsulator;
        if(encapsulator != null){return encapsulator;}
        throw new RuntimeException(tClass + " Does not exist");
    }

    void respondToCollision(float mag, float embX, float embY){for(Pair<?> i : this.pairs){i.encapsulator.getFeature().respondToCollision(mag, embX, embY);}}

    @Override public CollectingTypes.CollectingEncapsulator getCollectingEncapsulator() {return (CollectingTypes.CollectingEncapsulator) this.getFeature(Collecting.class);}
    @Override public SpawningTypes.SpawnerEncapsulator getSpawningEncapsulator() {return (SpawningTypes.SpawnerEncapsulator) this.getFeature(Spawner.class);}
    @Override public AttackTypes.AttackEncapsulator getAttackEncapsulator() {return (AttackTypes.AttackEncapsulator)this.getFeature(Attack.class);}
    @Override public HealthTypes.HealthEncapsulator getHealthEncapsulator() {return (HealthTypes.HealthEncapsulator) this.getFeature(Health.class);}
    @Override public PositioningTypes.DimensionsEncapsulator getDimensionsEncapsulator() {return (PositioningTypes.DimensionsEncapsulator) this.getFeature(ScalingAndPositioning.class);}
    @Override public HitBoxTypes.HitBoxEncapsulator getHitBoxEncapsulator() {return (HitBoxTypes.HitBoxEncapsulator) this.getFeature(HitBoxes.class);}
    @Override public MovementTypes.MovementEncapsulator getMovementEncapsulator() {return (MovementTypes.MovementEncapsulator) this.getFeature(Movement.class);}
    @SuppressWarnings("unchecked") <T extends Features> void setFeatureEncapsulator(FeatureEncapsulator<T> feature){this.setFeatureHelper((Pair<T>)this.pairs[mapping.get(feature.getClassObject())], feature);}
    private <T extends Features> void setFeatureHelper(Pair<T> pair, FeatureEncapsulator<T> encapsulator){pair.encapsulator = encapsulator;}
    void loopThroughAllFeatures(Consumer<FeatureEncapsulator<?>> operation){for(Pair<?> i : this.pairs){operation.accept(i.encapsulator);}}

    private final Pair<?>[] pairs = new Pair[Features.class.getPermittedSubclasses().length];
    private static final HashMap<Class<? extends Features>, Byte> mapping = new HashMap<>();
    private static class Pair<T extends Features>{
        private Pair(Class<T> classObject){this.classObject = classObject;}
        private FeatureEncapsulator<T> encapsulator;
        private final Class<T> classObject;
    }

    static{
        for(byte i = 0; i < Features.class.getPermittedSubclasses().length; i++){
            //noinspection unchecked
            Class<? extends Features> classObject = (Class<? extends Features>)Features.class.getPermittedSubclasses()[i];
            mapping.put(classObject, i);
        }
    }
}

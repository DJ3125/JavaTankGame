package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.HoldingFeature;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxable;
import com.example.finalproject_4_5_24.features.movement.*;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

import java.util.function.BiPredicate;

public final class GameElementEncapsulator<T extends DynamicGameElement> implements Located, Angled, Velocities, HitBoxable, HoldingFeature {
    public GameElementEncapsulator(T element){this.element = element;}
    String getImage(){return this.element.getImage();}

    @Override public boolean equals(Object obj){return obj instanceof GameElementEncapsulator<?> encapsulator && encapsulator.element == this.element;}
    public void addForce(Forces forceToAdd){this.element.addForce(forceToAdd);}
    @Override public float getX() {return this.element.getX();}
    @Override public float getY() {return this.element.getY();}

    public int getMass(){return this.element.getMass();}
    @Override public boolean contains(float x, float y) {return this.element.contains(x, y);}

    public GameLayer.EntityInitializationInfo gameEnvironment() {return this.element.gameEnvironment();}

    @Override public boolean isCollided(GameElementEncapsulator<?> encapsulator) {return this.element.isCollided(encapsulator);}
    @Override public boolean checkIfPointsAreInsideOther(BiPredicate<Float, Float> contains) {return this.element.checkIfPointsAreInsideOther(contains);}
    @Override public float getAngle() {return this.element.getAngle();}
    @Override public PositioningTypes.DimensionsEncapsulator getDimensionsEncapsulator() {return this.element.getDimensionsEncapsulator();}
    @Override public MovementTypes.MovementEncapsulator getMovementEncapsulator() {return this.element.getMovementEncapsulator();}
    @Override public HitBoxTypes.HitBoxEncapsulator getHitBoxEncapsulator() {return this.element.getHitBoxEncapsulator();}
    @Override public HealthTypes.HealthEncapsulator getHealthEncapsulator() {return this.element.getHealthEncapsulator();}
    @Override public AttackTypes.AttackEncapsulator getAttackEncapsulator() {return this.element.getAttackEncapsulator();}
    @Override public SpawningTypes.SpawnerEncapsulator getSpawningEncapsulator() {return this.element.getSpawningEncapsulator();}
    @Override public CollectingTypes.CollectingEncapsulator getCollectingEncapsulator() {return this.element.getCollectingEncapsulator();}

    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return this.element.isPotentialForCollision(encapsulator);}

    T getElement() {return this.element;}
    @SuppressWarnings("unchecked") public Class<T> getClassOfInstance(){return (Class<T>) this.element.getClass();}
    private final T element;

    @Override public float getAngAccel() {return this.element.getAngAccel();}
    @Override public float getAngVel() {return this.element.getAngVel();}
    @Override public float getxAccel() {return this.element.getxAccel();}
    @Override public float getyAccel() {return this.element.getyAccel();}
    @Override public float getxVel() {return this.element.getxVel();}
    @Override public float getyVel() {return this.element.getyVel();}
    @Override public float[][] getThisVertices() {return this.element.getThisVertices();}
}
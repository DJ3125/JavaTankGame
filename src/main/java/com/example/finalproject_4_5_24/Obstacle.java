package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.Forces;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

public final class Obstacle extends DynamicGameElement{
    public Obstacle(GameLayer.EntityInitializationInfo entityInitializationInfo,  float x, float y, float angle, float width, float height){
        super(entityInitializationInfo, MovementTypes.NULL_MOVEMENT, x, y, angle, 0, PositioningTypes.STATIC, width, height, 1, 1, HitBoxTypes.RECTANGLE);
//        this.setFeatureEncapsulator(HealthTypes.NORMAL_HEALTH.getEncapsulator(this.getInitializationInfo(), 1000));
    }
    @Override public String getImage() {return "wall";}

    @Override public void addForce(Forces forceToAdd) {}

    @Override public int getMass() {return 100;}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
//        System.out.println(notification.getChange());
        notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, true));
    }

    @Override protected void updateOther() {}

    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {
        Class<? extends DynamicGameElement> classObject = encapsulator.getClassOfInstance();
        return !DynamicGameElement.isSubClass(Obstacle.class, classObject) && !DynamicGameElement.isSubClass(Goal.class, classObject);
    }

    @Override public boolean readyForRemoval() {return false;}
}

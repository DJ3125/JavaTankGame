package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.Forces;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

public final class Goal extends DynamicGameElement {
    public Goal(GameLayer.EntityInitializationInfo entityInitializationInfo, float x, float y, float angle) {
        super(entityInitializationInfo, MovementTypes.NULL_MOVEMENT, x, y, angle, 0, PositioningTypes.STATIC, 0, 0, 400, 400, 1, 1, HitBoxTypes.RECTANGLE);
    }

    @Override public String getImage() {return "end";}

    @Override public void addForce(Forces forceToAdd) {}

    @Override public int getMass() {return Integer.MAX_VALUE;}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {if(DynamicGameElement.isSubClass(Player.class, notification.getOtherCollider().getClassOfInstance())){this.reachedTarget = true;}}

    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {
        return DynamicGameElement.isSubClass(Player.class, encapsulator.getClassOfInstance());
    }

    @Override protected void updateOther() {
        this.reachedTarget = false;
    }

    @Override public boolean readyForRemoval() {return false;}

    public boolean hasReachedTarget(){
        return this.reachedTarget;
    }

    private boolean reachedTarget;
}

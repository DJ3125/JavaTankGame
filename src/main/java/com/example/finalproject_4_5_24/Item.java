package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.collection.InventoryItem;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

abstract public sealed class Item extends DynamicGameElement permits HealthPackItem, NormalBulletAmmoItem {
    protected Item(GameLayer.EntityInitializationInfo entityInitializationInfo, float x, float y, float width, float height, InventoryItem itemContaining) {
        super(entityInitializationInfo, MovementTypes.INTERACT_MOVEMENT, x, y, 0, 0, PositioningTypes.STATIC, 0, 0, width, height, 1, 1, HitBoxTypes.RECTANGLE);
        this.itemContaining = itemContaining;
    }

    @Override public String getImage() {return this.itemContaining.image();}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        notification.getOtherCollider().getCollectingEncapsulator().addElement(this.itemContaining);
        if(!this.readyForRemoval()){notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, false));}
    }

    @Override public int getMass() {return 20;}
    @Override protected void updateOther() {this.getMovement().move();}
    @Override public boolean readyForRemoval() {
        return this.itemContaining.getCurrentItemCount() <= 0;
    }
    @Override public final boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return true;}

    private final InventoryItem itemContaining;
}

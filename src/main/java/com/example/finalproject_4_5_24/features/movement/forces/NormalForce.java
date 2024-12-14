package com.example.finalproject_4_5_24.features.movement.forces;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.Forces;

public final class NormalForce extends Forces {
    public NormalForce(GameElementEncapsulator<?> encapsulator, HitBoxes.ModifiedCollisionNotification notification, boolean isApplierImmovable) {
        super(encapsulator);
        this.priority = (byte) (4 + (isApplierImmovable ? 1 : 0));
        this.notification = notification;
    }

    @Override protected byte priority() {return this.priority;}

    @Override protected float getAccelerationChangeForX() {return (float) (Math.cos(this.notification.getAngle()) * (this.notification.getChange() * this.dampening) + Math.cos(this.notification.getEmbAngle()) * this.getMagnitude() * this.getEncapsulator().getMass());}
    @Override protected float getAccelerationChangeForY() {return (float) (Math.sin(this.notification.getAngle()) * (this.notification.getChange() * this.dampening) + Math.sin(this.notification.getEmbAngle()) * this.getMagnitude() * this.getEncapsulator().getMass());}


    //    @Override protected float getAccelerationChangeForX() {return (float) (Math.cos(this.notification.getAngle()) * (this.notification.getChange() * this.dampening) + Math.cos(this.notification.getEmbAngle()) * this.getMagnitude() * this.getEncapsulator().getMass());}
//    @Override protected float getAccelerationChangeForY() {return (float) (Math.sin(this.notification.getAngle()) * (this.notification.getChange() * this.dampening) + Math.sin(this.notification.getEmbAngle()) * this.getMagnitude() * this.getEncapsulator().getMass());}
    private float getMagnitude(){return (float)Math.sqrt(Math.pow(this.notification.getMagnitudeEmbeddedX(), 2) + Math.pow(this.notification.getMagnitudeEmbeddedY(), 2));}
    private final byte priority;
    private final HitBoxes.ModifiedCollisionNotification notification;
    private final float dampening = 0f;
}

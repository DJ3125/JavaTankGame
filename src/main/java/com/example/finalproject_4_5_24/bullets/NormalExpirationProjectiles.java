package com.example.finalproject_4_5_24.bullets;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;

public final class NormalExpirationProjectiles extends ExpirationProjectiles {
    public NormalExpirationProjectiles(GameLayer.EntityInitializationInfo entityInitializationInfo, BulletSourceInformation info, float x, float y, float angle) {super(entityInitializationInfo, info, x, y, angle, 10, 100, true);}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        super.respondToCollision(notification);
        this.isDead = true;
    }

    @Override public boolean readyForRemoval() {return this.isDead || this.getTimer().performBehavior();}

    @Override public String getImage() {return "bullet";}
    private boolean isDead = false;
}

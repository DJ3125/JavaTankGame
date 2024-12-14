package com.example.finalproject_4_5_24.bullets;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;

public final class BranchingFlameExpirationBullet extends ExpirationProjectiles{
    public BranchingFlameExpirationBullet(GameLayer.EntityInitializationInfo entityInitializationInfo, BulletSourceInformation info, float x, float y, float angle) {
        super(entityInitializationInfo, info, x, y, angle, 10, tickRange, true);
        this.originalAngle = this.getAngle();
        this.getIteration = 0;
    }

    private BranchingFlameExpirationBullet(GameLayer.EntityInitializationInfo entityInitializationInfo, BulletSourceInformation info, BranchingFlameExpirationBullet source, float angle){
        super(entityInitializationInfo, info, source.getX(), source.getY(), angle, (int)(source.getAttack().getNormalAttack() * 0.1), tickRange, false);
        this.getIteration = source.getIteration + 1;
        this.originalAngle = source.originalAngle;
    }

    @Override public String getImage() {return "fire";}

    @Override public boolean readyForRemoval() {
        if(this.isDead){return true;}
        if(this.getTimer().performBehavior()){
            if(this.getIteration < maxIteration){
                byte range = 4;
                for(byte i = (byte) -range; i <= range; i++){this.gameEnvironment().operator().addElement(new BranchingFlameExpirationBullet(this.gameEnvironment(), this.getSourceInfo(), this, (float) (AnglesAndTrig.getAngle(this.getxVel(), this.getyVel()) + i * Math.PI/4/range)));}
            }
            return true;
        }else{return false;}
    }

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        super.respondToCollision(notification);
        this.isDead = true;
    }

    private final float originalAngle;
    private final int getIteration;
    private boolean isDead = false;
    private static final int maxIteration = 1, tickRange = 15;
}

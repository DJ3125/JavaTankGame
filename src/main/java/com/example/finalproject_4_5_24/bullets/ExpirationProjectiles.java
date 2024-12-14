package com.example.finalproject_4_5_24.bullets;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.Enemies;
import com.example.finalproject_4_5_24.Obstacle;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.LocomotiveForce;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.timers.ActivationTimer;

import java.util.Arrays;
import java.util.Iterator;

abstract public sealed class ExpirationProjectiles extends DynamicGameElement permits BranchingFlameExpirationBullet, NormalExpirationProjectiles {
    protected ExpirationProjectiles(GameLayer.EntityInitializationInfo entityInitializationInfo, BulletSourceInformation info, float x, float y, float angle, int attackAmount, int tickRange, boolean justFired){
        super(entityInitializationInfo, MovementTypes.LINEAR_MOVEMENT, x, y, angle, 4f + getAdditionalForce(info.getEncapsulator(), angle), PositioningTypes.STATIC, 40, 40, 1, 1, HitBoxTypes.RECTANGLE);
        if(justFired){this.addForce(new LocomotiveForce(this.getEncapsulator(), (float) (80 * Math.cos(this.getAngle())), (float) (80 * Math.sin(this.getAngle()))));}
        this.setFeatureEncapsulator(AttackTypes.NORMAL_ATTACK.getEncapsulator(this.getInitializationInfo(), attackAmount));
        this.sourceInfo = info;
        this.timer = new ActivationTimer(tickRange);
    }

    @Override public int getMass() {return 1;}

    private static float getAdditionalForce(GameElementEncapsulator<?> encapsulator, float myAng){
        if(encapsulator.getxVel() != 0 || encapsulator.getyVel() != 0){
            double otherAngle = AnglesAndTrig.getAngle(encapsulator.getxVel(), encapsulator.getyVel());
            double semiDot = 3 * Math.cos(myAng - otherAngle);
            return (float) semiDot;
        }else{return 0;}
    }

    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {
        Class<? extends DynamicGameElement>[] targets = new Class[]{Obstacle.class, Enemies.class};
        boolean found = false;
        Iterator<Class<? extends DynamicGameElement>> iterator = Arrays.stream(targets).iterator();
        while (iterator.hasNext() && !found) {found = DynamicGameElement.isSubClass(iterator.next(), encapsulator.getClassOfInstance());}
        return found && (!encapsulator.equals(this.sourceInfo.getEncapsulator()));
    }
    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, false));
        this.getAttack().attack(notification.getOtherCollider().getHealthEncapsulator());
    }
    protected final ActivationTimer getTimer(){return this.timer;}

    @Override protected void updateOther() {
        this.timer.incrementTimer();
        this.getMovement().move();
    }

    protected final BulletSourceInformation getSourceInfo(){return this.sourceInfo;}

    private final BulletSourceInformation sourceInfo;
    private final ActivationTimer timer;
}

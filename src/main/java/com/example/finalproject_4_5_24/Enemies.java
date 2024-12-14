package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.bullets.ExpirationProjectiles;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

public final class Enemies extends DynamicGameElement {

    public Enemies(GameLayer.EntityInitializationInfo entityInitializationInfo,  float x, float y) {
        super(entityInitializationInfo, MovementTypes.PLAYER_HOMING_MOVEMENT, x, y, 0, 2.5f, PositioningTypes.STATIC, 100, 100, 1, 1, HitBoxTypes.RECTANGLE);
        this.setFeatureEncapsulator(HealthTypes.NORMAL_HEALTH.getEncapsulator(this.getInitializationInfo(), 50));
        this.setFeatureEncapsulator(AttackTypes.NORMAL_ATTACK.getEncapsulator(this.getInitializationInfo(), 1));
    }

    @Override public int getMass() {return 25;}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
//        NormalForce normalForce = new NormalForce(notification.getOtherCollider(), notification.getAngle(), false, notification.getMagnitudeEmbeddedX(), notification.getMagnitudeEmbeddedY());
        notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, false));
    }

    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return !DynamicGameElement.isSubClass(Goal.class, encapsulator.getClassOfInstance());}

    @Override public String getImage() {
        return "monster";
    }

    @Override protected void updateOther() {
        this.getMovement().move();
        for(GameElementEncapsulator<? extends Player> i : this.gameEnvironment().operator().getAllEntitiesThatSatisfyCondition(Player.class, this::isCollided)){
            this.getAttack().attack(i.getHealthEncapsulator());
        }
    }

    @Override public boolean readyForRemoval() {return this.getHealth().isDead();}
}

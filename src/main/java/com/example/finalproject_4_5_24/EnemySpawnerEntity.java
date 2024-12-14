package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

abstract public sealed class EnemySpawnerEntity extends DynamicGameElement permits StaticEnemySpawner {

    protected EnemySpawnerEntity(GameLayer.EntityInitializationInfo entityInitializationInfo, MovementTypes movementType, float x, float y, float angle) {
        super(entityInitializationInfo, movementType, x, y, angle, 0, PositioningTypes.STATIC, 150, 150, 1, 1, HitBoxTypes.RECTANGLE);
        this.setFeatureEncapsulator(HealthTypes.NORMAL_HEALTH.getEncapsulator(this.getInitializationInfo(), 200));
        this.setFeatureEncapsulator(SpawningTypes.ENEMY_SPAWNER.getEncapsulator(this.getInitializationInfo(), 1000));
    }

    @Override public String getImage() {return "nest";}
    @Override public int getMass() {return Integer.MAX_VALUE;}

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, true));
    }

    @Override protected void updateOther() {
        this.getSpawner().spawn();
    }

    @Override public boolean readyForRemoval() {return this.getHealth().isDead();}
    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return !DynamicGameElement.isSubClass(Enemies.class, encapsulator.getClassOfInstance());}
}

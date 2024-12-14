package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.bullets.ExpirationProjectiles;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.movement.forces.NormalForce;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;
import javafx.scene.input.KeyCode;

public final class Player extends DynamicGameElement {
    private Player(GameLayer.EntityInitializationInfo entityInitializationInfo, float xPosition, float yPosition){
        super(entityInitializationInfo, MovementTypes.PLAYER_MOVEMENT, xPosition, yPosition, 0, 3, PositioningTypes.STATIC, 0, 0, 200, 200, (float)1.5, (float)1.5, HitBoxTypes.RECTANGLE);
        this.setFeatureEncapsulator(HealthTypes.NORMAL_HEALTH.getEncapsulator(this.getInitializationInfo(), 200));
        this.setFeatureEncapsulator(AttackTypes.NORMAL_ATTACK.getEncapsulator(this.getInitializationInfo(), 1));
        this.setFeatureEncapsulator(SpawningTypes.PLAYER_BULLET_SPAWNER.getEncapsulator(this.getInitializationInfo(), 1));
        this.setFeatureEncapsulator(CollectingTypes.NORMAL_COLLECTING.getEncapsulator(this.getInitializationInfo(), 20));
    }

    public static GameElementEncapsulator<Player> getEncapsulator(GameLayer.EntityInitializationInfo entityInitializationInfo, float xPosition, float yPosition){return new GameElementEncapsulator<>(new Player(entityInitializationInfo, xPosition, yPosition));}

    @Override public int getMass() {
        return 100;
    }

    @Override protected void respondToCollision(HitBoxes.ModifiedCollisionNotification notification) {
        notification.getOtherCollider().addForce(new NormalForce(notification.getOtherCollider(), notification, false));
    }
    @Override public boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {
        if(DynamicGameElement.isSubClass(ExpirationProjectiles.class, encapsulator.getClassOfInstance())){return encapsulator.isPotentialForCollision(this.getEncapsulator());}
        return true;
    }

    @Override public String getImage() {return "tank";}

    @Override public boolean readyForRemoval(){return this.getHealth().isDead();}
    @Override protected void updateOther(){
        if(this.gameEnvironment().inputs().getValueOfKeyCode(KeyCode.R)){this.getSpawner().reload(100);}
        else if(this.gameEnvironment().inputs().getValueOfKeyCode(KeyCode.SHIFT)){this.getSpawner().spawn();}
        this.getMovement().move();
    }
}

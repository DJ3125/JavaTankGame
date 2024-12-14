package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;

public final class StaticEnemySpawner extends EnemySpawnerEntity{

    public StaticEnemySpawner(GameLayer.EntityInitializationInfo entityInitializationInfo, float x, float y, float angle) {
        super(entityInitializationInfo, MovementTypes.NULL_MOVEMENT, x, y, angle);
    }
}

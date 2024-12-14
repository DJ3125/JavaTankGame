package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.movement.MovementTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

public sealed interface HoldingFeature permits GameElementEncapsulator, DynamicGameElement, FeatureHolder {
    MovementTypes.MovementEncapsulator getMovementEncapsulator();
    PositioningTypes.DimensionsEncapsulator getDimensionsEncapsulator();
    HitBoxTypes.HitBoxEncapsulator getHitBoxEncapsulator();
    HealthTypes.HealthEncapsulator getHealthEncapsulator();
    AttackTypes.AttackEncapsulator getAttackEncapsulator();
    SpawningTypes.SpawnerEncapsulator getSpawningEncapsulator();
    CollectingTypes.CollectingEncapsulator getCollectingEncapsulator();
}

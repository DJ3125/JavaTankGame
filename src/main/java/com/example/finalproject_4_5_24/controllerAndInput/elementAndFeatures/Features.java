package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.attack.Attack;
import com.example.finalproject_4_5_24.features.collection.Collecting;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import com.example.finalproject_4_5_24.features.health.Health;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.Movement;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.ScalingAndPositioning;
import com.example.finalproject_4_5_24.features.spawning.Spawner;

abstract sealed public class Features permits Attack, Collecting, Health, HitBoxes, Movement, ScalingAndPositioning, Spawner {
    protected Features(DynamicGameElement.FeatureInitializationInfo featureInitializationInfo){this.info = featureInitializationInfo;}
    protected abstract void updateOnTurn();
    protected abstract void updateOnTick();
    protected abstract void updateOnChange();


    protected void respondToItem(InventoryItemOptions.ItemModification itemModification){}
    protected void respondToCollision(float magnitudeOfChange, float embX, float embY){}

    protected final GameElementEncapsulator<?> getEncapsulator(){return this.info.encapsulator();}
    protected final ElementNotifier getNotifier(){return this.info.notifier();}
    private final DynamicGameElement.FeatureInitializationInfo info;
}
package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.collection.InventoryItem;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.InventoryTypes;

public final class HealthPackItem extends Item{
    public HealthPackItem(GameLayer.EntityInitializationInfo entityInitializationInfo, float x, float y, float width, float height) {
        super(entityInitializationInfo, x, y, width, height, InventoryTypes.HEALTH_PACK.getInventoryItem(3));
    }
}

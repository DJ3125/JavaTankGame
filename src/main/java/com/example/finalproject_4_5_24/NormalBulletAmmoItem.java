package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.InventoryTypes;

public final class NormalBulletAmmoItem extends Item{
    public NormalBulletAmmoItem(GameLayer.EntityInitializationInfo entityInitializationInfo, float x, float y, float width, float height) {super(entityInitializationInfo, x, y, width, height, InventoryTypes.NORMAL_BULLET_CARTRIDGE.getInventoryItem(10));}
}

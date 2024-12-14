package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.NormalBulletAmmo;

public final class AmmoUsageOption extends InventoryItemOptions<NormalBulletAmmo> {
    public AmmoUsageOption(NormalBulletAmmo inventoryItem) {super(inventoryItem, "Use Ammo");}
    @Override public void performAction(GameElementEncapsulator<?> appliedOn) {this.decreaseInventoryItem(-1);}
    @Override public boolean canUse(GameElementEncapsulator<?> encapsulator) {return this.getInventoryItem().getCurrentItemCount() > 0;}
}

package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.HealthPack;

public final class HealOption extends InventoryItemOptions<HealthPack> {
    public HealOption(HealthPack inventoryItem, int healAmount) {
        super(inventoryItem, "Heal");
        this.healAmount = healAmount;
    }

    @Override public boolean canUse(GameElementEncapsulator<?> encapsulator) {return this.getInventoryItem().getCurrentItemCount() > 0;}

    @Override public void performAction(GameElementEncapsulator<?> appliedOn) {
        this.decreaseInventoryItem(-1);
        ItemModification request = (new ItemRequestBuilder()).setHealthChange(this.healAmount).getRequest();
        this.createRequest(appliedOn, request);
    }

    private final int healAmount;
}

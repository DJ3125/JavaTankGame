package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.collection.InventoryItem;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class HealthPack extends InventoryItem {
    HealthPack(int initialItemCount, int maxItemCount) {
        super(initialItemCount, maxItemCount);
    }

    @Override protected InventoryItem clone() {return new HealthPack(this.getCurrentItemCount(), this.getMaxItemCount());}
    @Override protected void useItem(GameElementEncapsulator<?> encapsulator) {
        this.decreaseItemCount(-1);
        this.healOption.performAction(encapsulator);
    }

    @Override public String image() {return "healthPack";}
    @Override public String name() {return "Health Pack";}
    @Override public @NotNull Iterator<InventoryItemOptions<?>> iterator() {return new ItemUsageIterator(this.healOption);}
    private final HealOption healOption = new HealOption(this, 50);
}

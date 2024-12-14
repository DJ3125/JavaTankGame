package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.collection.InventoryItem;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;

import java.util.Iterator;

public final class NormalBulletAmmo extends InventoryItem {
    NormalBulletAmmo(int initialItemCount, int maxItemCount) {super(initialItemCount, maxItemCount);}
    @Override public String image() {return "x";}
    @Override public String name() {return "Normal Bullet Ammo";}
    @Override protected void useItem(GameElementEncapsulator<?> encapsulator) {this.ammoUsageOption.performAction(encapsulator);}
    @Override public Iterator<InventoryItemOptions<?>> iterator() {return new ItemUsageIterator(this.ammoUsageOption);}
    @Override protected NormalBulletAmmo clone(){return new NormalBulletAmmo(this.getCurrentItemCount(), this.getMaxItemCount());}
    private final AmmoUsageOption ammoUsageOption = new AmmoUsageOption(this);
}

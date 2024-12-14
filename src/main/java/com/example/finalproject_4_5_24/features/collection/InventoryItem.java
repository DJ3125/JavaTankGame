package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.HealthPack;
import com.example.finalproject_4_5_24.features.collection.inventoryTypes.NormalBulletAmmo;

abstract public sealed class InventoryItem implements Iterable<InventoryItemOptions<?>> permits HealthPack, NormalBulletAmmo {
    protected InventoryItem(int initialItemCount, int maxItemCount){
        this.currentItemCount = initialItemCount;
        this.maxItemCount = maxItemCount;
    }
    @Override abstract protected InventoryItem clone();
    abstract protected void useItem(GameElementEncapsulator<?> encapsulator);
    public abstract String image();
    public abstract String name();

    public final int getCurrentItemCount() {return currentItemCount;}
    public final int getMaxItemCount() {return maxItemCount;}
    final int getAmountOfRoom(){return this.maxItemCount - this.currentItemCount;}
    protected final int increaseItemCount(int amount){
        if(amount < 0){throw new RuntimeException("You're using the wrong method");}
        this.currentItemCount += amount;
        if(this.currentItemCount > this.maxItemCount){
            int subtract = this.currentItemCount - this.maxItemCount;
            this.currentItemCount -= subtract;
            return subtract;
        }else{return 0;}
    }

    protected final void decreaseItemCount(int amount){
        if(amount > 0){throw new RuntimeException("You're using the wrong method");}
        this.currentItemCount += amount;
        this.currentItemCount = Math.max(0, this.currentItemCount);
    }

    private int currentItemCount;
    private final int maxItemCount;
}

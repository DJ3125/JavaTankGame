package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.attack.AttackPacket;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;

abstract public class InventoryItemOptions<T extends InventoryItem>{
    protected InventoryItemOptions(T inventoryItem, String name){
        this.inventoryItem = inventoryItem;
        this.name = name;
    }


    public abstract boolean canUse(GameElementEncapsulator<?> encapsulator);
    protected final InventoryItem getInventoryItem(){return this.inventoryItem;}
    public String getName(){return this.name;}
    protected final void decreaseInventoryItem(int amount){this.inventoryItem.decreaseItemCount(amount);}
    protected final void increaseInventoryItem(int amount){this.inventoryItem.increaseItemCount(amount);}

    public abstract void performAction(GameElementEncapsulator<?> appliedOn);
    protected final void createRequest(GameElementEncapsulator<?> encapsulator, ItemModification request){encapsulator.getCollectingEncapsulator().getItemNotifier().accept(request);}
    private final T inventoryItem;
    private final String name;

    public static final class ItemModification{
        private ItemModification(){}
        public int getHealthChange() {return this.healthChange;}
        private int healthChange = 0;
    }

    protected static final class ItemRequestBuilder{
        public ItemRequestBuilder(){}
        public ItemRequestBuilder setHealthChange(int healthChange){
            this.request.healthChange = healthChange;
            return this;
        }
        public ItemModification getRequest(){return this.request;}
        private final ItemModification request = new ItemModification();
    }
}

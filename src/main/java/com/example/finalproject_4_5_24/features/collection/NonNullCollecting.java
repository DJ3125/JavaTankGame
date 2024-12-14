package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;

abstract public sealed class NonNullCollecting extends Collecting permits NormalCollecting {
    protected NonNullCollecting(DynamicGameElement.FeatureInitializationInfo featureInitializationInfo, int collectionSize) {
        super(featureInitializationInfo);
        this.collectionSize = collectionSize;
    }


    @Override protected void updateOnTurn() {
        this.collection.removeIf(current -> current.getCurrentItemCount() <= 0);
//        this.collection.removeIf(inventoryItemOptions -> inventoryItemOptions.getCurrentItemCount() <= 0);
    }
    @Override protected void updateOnTick() {}
    @Override protected void updateOnChange() {}

    @Override public void dropItem(InventoryItem item) {}

    @Override public boolean useElement(Class<? extends InventoryItem> itemClass) {

        return false;
    }
    @Override public void dropAll() {

    }

    @Override protected Consumer<InventoryItemOptions.ItemModification> useItemRequest() {return itemModification -> {this.getNotifier().notifyOfItemUsage(itemModification);};}

    @Override public final void addElement(InventoryItem item) {
        {
            Iterator<InventoryItem> iterator = this.collection.iterator();
            while(iterator.hasNext() && item.getCurrentItemCount() > 0){
                InventoryItem current = iterator.next();
                if(current.getClass() == item.getClass()){
                    int room = Math.min(current.getAmountOfRoom(), item.getCurrentItemCount());
                    current.increaseItemCount(room);
                    item.decreaseItemCount(-room);
                }
            }
        }
        if(item.getCurrentItemCount() > 0 && this.collection.size() < this.collectionSize){
            this.collection.add(item.clone());
            item.decreaseItemCount(-item.getCurrentItemCount());
        }
    }

    @Override public boolean isEmpty() {
        for(InventoryItem i : this.collection){if(i != null){return false;}}
        return true;
    }

    @Override public final int getAmountOfElement(Class<? extends InventoryItem> itemClass) {
        int counter = 0;
        for(InventoryItem i : this.collection){if(i.getClass() == itemClass){counter += i.getCurrentItemCount();}}
        return counter;
    }
    @Override public final int getInventorySize() {return this.collectionSize;}

    @NotNull @Override public final Iterator<InventoryItem> iterator() {return new InventoryIterator(this.collection);}

    private final ArrayList<InventoryItem> collection = new ArrayList<>();
    private int collectionSize;

    private static final class InventoryIterator implements Iterator<InventoryItem>{
        private InventoryIterator(@NotNull ArrayList<InventoryItem> collection){
            this.collection = collection;
            this.originalSize = collection.size();
        }
        @Override public boolean hasNext() {return this.current < this.collection.size();}
        @Override public InventoryItem next(){
            if(this.collection.size() != this.originalSize){throw new ConcurrentModificationException("WHY DID IT CHANGE");}
            if(this.current >= this.originalSize){throw new RuntimeException("There Is No Next");}
            return this.collection.get(this.current++);
        }
        private final int originalSize;
        private int current;
        private final ArrayList<InventoryItem> collection;
    }
}

package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;

import java.util.Iterator;

public final class ItemUsageIterator implements Iterator<InventoryItemOptions<?>>{
    public ItemUsageIterator(InventoryItemOptions<?>... options){this.options = options;}
    @Override public boolean hasNext() {return this.counter < this.options.length;}

    @Override public InventoryItemOptions<?> next() {
        if(!this.hasNext()){throw new RuntimeException("Cannot use Iterator if there is no next");}
        return this.options[this.counter++];
    }

    private int counter = 0;
    private final InventoryItemOptions<?>[] options;
}

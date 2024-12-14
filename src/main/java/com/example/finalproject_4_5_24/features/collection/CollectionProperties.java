package com.example.finalproject_4_5_24.features.collection;

public sealed interface CollectionProperties extends Iterable<InventoryItem> permits Collecting, CollectingTypes.CollectingEncapsulator {
    void addElement(InventoryItem item);
    int getAmountOfElement(Class<? extends InventoryItem> itemClass);
    int getInventorySize();
    boolean isEmpty();
}

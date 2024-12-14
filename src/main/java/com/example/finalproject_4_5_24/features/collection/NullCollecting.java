package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

final class NullCollecting extends Collecting{
    NullCollecting(DynamicGameElement.FeatureInitializationInfo featureInitializationInfo) {super(featureInitializationInfo);}
    @Override protected void updateOnTurn() {}
    @Override protected void updateOnTick() {}
    @Override protected void updateOnChange() {}
    @Override public boolean useElement(Class<? extends InventoryItem> itemClass) {return false;}
    @Override public void dropAll() {}
    @Override public void addElement(InventoryItem item) {}
    @Override public int getAmountOfElement(Class<? extends InventoryItem> itemClass) {return 0;}
    @Override public int getInventorySize() {return 0;}
    @Override public void dropItem(InventoryItem item) {}
    @NotNull @Override public Iterator<InventoryItem> iterator() {return new NullIterator();}
    private static final class NullIterator implements Iterator<InventoryItem>{
        @Override public boolean hasNext() {return false;}
        @Override public InventoryItem next() {throw new RuntimeException("There Is No Next");}
    }
    @Override protected Consumer<InventoryItemOptions.ItemModification> useItemRequest() {return itemModification -> {};}
}

package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;

import java.util.function.Consumer;

abstract public sealed class Collecting extends Features implements CollectionProperties permits NonNullCollecting, NullCollecting {
    protected Collecting(DynamicGameElement.FeatureInitializationInfo featureInitializationInfo) {super(featureInitializationInfo);}
    abstract public boolean useElement(Class<? extends InventoryItem> itemClass);
    abstract public void dropAll();
    abstract public void dropItem(InventoryItem item);
    public boolean isEmpty(){return true;}
    protected abstract Consumer<InventoryItemOptions.ItemModification> useItemRequest();
}

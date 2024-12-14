package com.example.finalproject_4_5_24.features.collection;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public enum CollectingTypes {
    NULL_COLLECTING{@Override public final FeatureEncapsulator<Collecting> getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int collectionSize) {return this.encapsulate(new NullCollecting(info));}},
    NORMAL_COLLECTING{@Override public final FeatureEncapsulator<Collecting> getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int collectionSize) {return this.encapsulate(new NormalCollecting(info, collectionSize));}};

    protected final FeatureEncapsulator<Collecting> encapsulate(Collecting feature){return new CollectingEncapsulator(feature);}
    public abstract FeatureEncapsulator<Collecting> getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int collectionSize);

    public static final class CollectingEncapsulator extends FeatureEncapsulator<Collecting> implements CollectionProperties{
        private CollectingEncapsulator(Collecting feature) {super(Collecting.class, feature);}
        @Override public void addElement(InventoryItem item) {this.getFeature().addElement(item);}
        @Override public int getAmountOfElement(Class<? extends InventoryItem> itemClass) {return this.getFeature().getAmountOfElement(itemClass);}
        @Override public int getInventorySize() {return this.getFeature().getInventorySize();}
        @Override public boolean isEmpty() {return this.getFeature().isEmpty();}
        @NotNull @Override public Iterator<InventoryItem> iterator() {return this.getFeature().iterator();}
        Consumer<InventoryItemOptions.ItemModification> getItemNotifier(){return this.getFeature().useItemRequest();}
    }
}

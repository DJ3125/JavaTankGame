package com.example.finalproject_4_5_24.controllerAndInput.levels.layers;

import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.InventoryViewing;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.NullTertiaryUIElement;

import java.util.function.Consumer;

public sealed abstract class TertiaryLayerUIElements extends LevelLayers<Level> permits InventoryViewing, NullTertiaryUIElement {
    protected TertiaryLayerUIElements(Level level, Consumer<TertiaryLayerUIElements> switchingBack){
        super(level);
        this.switchingBack = switchingBack;
    }
    protected final void returnToPlaying(TertiaryLayerUIElements switchingTo){this.switchingBack.accept(switchingTo);}
    protected final Consumer<TertiaryLayerUIElements> switchingBack;
}

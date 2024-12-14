package com.example.finalproject_4_5_24.bullets;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

public final class BulletSourceInformation {
    public BulletSourceInformation(GameElementEncapsulator<?> encapsulator){this.encapsulator = encapsulator;}

    @Override public boolean equals(Object obj) {return obj instanceof BulletSourceInformation info && info.encapsulator.equals(this.encapsulator);}

    public GameElementEncapsulator<?> getEncapsulator() {return this.encapsulator;}
    private final GameElementEncapsulator<? extends DynamicGameElement> encapsulator;
}

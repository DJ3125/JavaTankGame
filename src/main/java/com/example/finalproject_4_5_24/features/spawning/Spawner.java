package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;

abstract sealed public class Spawner extends Features implements SpawningProperties permits MultipleSpawner, NullSpawning, SingleSpawner {
    protected Spawner(DynamicGameElement.FeatureInitializationInfo info){super(info);}
    public abstract void spawn();
    public abstract int reload(int amountGiven);
    public abstract Class<? extends DynamicGameElement> getSpawningType();
}

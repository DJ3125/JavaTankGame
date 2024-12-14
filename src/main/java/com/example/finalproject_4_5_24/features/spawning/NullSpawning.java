package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class NullSpawning extends Spawner{
    NullSpawning(DynamicGameElement.FeatureInitializationInfo info){super(info);}
    @Override protected void updateOnTurn() {}
    @Override public Class<? extends DynamicGameElement> getSpawningType() {return null;}
    @Override protected void updateOnTick() {}
    @Override protected void updateOnChange() {}
    @Override public void spawn() {}
    @Override public int getSpawnRateCoolDown() {return 0;}
    @Override public int getNormalSpawnRateCoolDown() {return 0;}
    @Override public boolean canSpawn() {return false;}
    @Override public boolean requiresAmmo() {return false;}
    @Override public int getMaxCapacity() {return 0;}
    @Override public int getCurrentCapacity() {return 0;}
    @Override public int reload(int amountGiven) {return amountGiven;}
    @Override public int getReloadRate() {return Integer.MAX_VALUE;}
    @Override public int reloadIncrement() {return 0;}
}

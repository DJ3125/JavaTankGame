package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

import java.util.ArrayList;
import java.util.Arrays;

abstract sealed class MultipleSpawner extends Spawner permits PlayerBulletSpawner {
    MultipleSpawner(DynamicGameElement.FeatureInitializationInfo info, SingleSpawner... spawners) {
        super(info);
        this.spawners = new ArrayList<>(Arrays.stream(spawners).toList());
        this.currentSpawner = this.spawners.get(this.selectedSpawner);
    }
    @Override protected void updateOnTurn() {
        for(SingleSpawner i : this.spawners){i.updateOnTurn();}
    }
    @Override public final void spawn() {this.currentSpawner.spawn();}
    @Override public int getNormalSpawnRateCoolDown() {return this.currentSpawner.getNormalSpawnRateCoolDown();}
    @Override public int getSpawnRateCoolDown() {return this.currentSpawner.getSpawnRateCoolDown();}
    @Override protected void updateOnChange() {}
    @Override protected void updateOnTick() {}
    @Override public int reloadIncrement() {return this.currentSpawner.reloadIncrement();}
    @Override public int getReloadRate() {return this.currentSpawner.getReloadRate();}
    @Override public final boolean canSpawn() {return this.currentSpawner.canSpawn();}
    @Override public final int reload(int amountGiven) {return this.currentSpawner.reload(amountGiven);}
    protected final void changeSelectedSpawner(byte amount){this.setSelectedSpawner((byte) (this.selectedSpawner + amount));}
    @Override public Class<? extends DynamicGameElement> getSpawningType() {return this.currentSpawner.getSpawningType();}
    @Override public final boolean requiresAmmo() {return this.currentSpawner.requiresAmmo();}
    @Override public final int getCurrentCapacity() {return this.currentSpawner.getCurrentCapacity();}
    @Override public final int getMaxCapacity() {return this.currentSpawner.getMaxCapacity();}

    protected final void setSelectedSpawner(byte selectedSpawner) {
        this.selectedSpawner = (byte) (((selectedSpawner % this.spawners.size()) + this.spawners.size()) % this.spawners.size());
        this.currentSpawner = this.spawners.get(this.selectedSpawner);
    }
    protected final SingleSpawner getCurrentSpawner(){return this.currentSpawner;}
    private final ArrayList<SingleSpawner> spawners;
    private byte selectedSpawner = 0;
    private SingleSpawner currentSpawner;
}

package com.example.finalproject_4_5_24.features.spawning;

sealed interface SpawningProperties permits Spawner, SpawningTypes.SpawnerEncapsulator {
    int getSpawnRateCoolDown();
    int getNormalSpawnRateCoolDown();
    boolean canSpawn();
    boolean requiresAmmo();
    int getMaxCapacity();
    int getCurrentCapacity();
    int getReloadRate();
    int reloadIncrement();
}

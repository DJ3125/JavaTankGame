package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;

public enum SpawningTypes {
    NULL_SPAWNER{@Override public SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown) {return this.encapsulate(new NullSpawning(info));}},
    ENEMY_SPAWNER{@Override public final SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown) {return this.encapsulate(new EnemySpawner(info, coolDown));}},
    PLAYER_BULLET_SPAWNER{@Override public final SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown) {return this.encapsulate(new PlayerBulletSpawner(info));}},
    FLAME_BULLET_SPAWNER{@Override public final SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown) {return this.encapsulate(new FlameBulletSpawner(info, coolDown));}},
    NORMAL_BULLET_SPAWNER {@Override public final SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown) {return this.encapsulate(new NormalBulletSpawner(info, coolDown));}};
    public abstract SpawnerEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int coolDown);
    protected final SpawnerEncapsulator encapsulate(Spawner spawner){return new SpawnerEncapsulator(spawner);}
    public static final class SpawnerEncapsulator extends FeatureEncapsulator<Spawner> implements SpawningProperties{
        private SpawnerEncapsulator(Spawner feature) {super(Spawner.class, feature);}
        @Override public int getSpawnRateCoolDown() {return this.getFeature().getSpawnRateCoolDown();}
        @Override public int getNormalSpawnRateCoolDown() {return this.getFeature().getNormalSpawnRateCoolDown();}
        @Override public boolean canSpawn() {return this.getFeature().canSpawn();}
        @Override public boolean requiresAmmo() {return this.getFeature().requiresAmmo();}
        @Override public int getMaxCapacity() {return this.getFeature().getMaxCapacity();}
        @Override public int getCurrentCapacity() {return this.getFeature().getCurrentCapacity();}
        @Override public int reloadIncrement() {return this.getFeature().reloadIncrement();}
        @Override public int getReloadRate() {return this.getFeature().getReloadRate();}
        public Class<? extends DynamicGameElement> getSpawningType(){return this.getFeature().getSpawningType();}
    }
}

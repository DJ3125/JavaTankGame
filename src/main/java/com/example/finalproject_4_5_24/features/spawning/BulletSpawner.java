package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;

abstract sealed class BulletSpawner extends SingleSpawner permits FlameBulletSpawner, NormalBulletSpawner {
    BulletSpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown, int max, int current, int reloadRate, int reloadIncrement) {
        super(info, spawnRateCoolDown, max, current, reloadRate, reloadIncrement);
    }

    @Override public void spawn(){
        if(this.canSpawn() && this.useAmmo()){
            this.getTimer().performBehavior();
            GameElementEncapsulator<?> thisE = this.getEncapsulator();
            this.addBullet(thisE);
        }
    }

    protected abstract void addBullet(GameElementEncapsulator<?> thisE);
}

package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.bullets.BranchingFlameExpirationBullet;
import com.example.finalproject_4_5_24.bullets.BulletSourceInformation;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class FlameBulletSpawner extends BulletSpawner{
    FlameBulletSpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown) {super(info, spawnRateCoolDown, 50, 50, 10, 1);}
    @Override public Class<? extends DynamicGameElement> getSpawningType() {return BranchingFlameExpirationBullet.class;}
    @Override protected void addBullet(GameElementEncapsulator<?> thisE) {this.getEncapsulator().gameEnvironment().operator().addElement(new BranchingFlameExpirationBullet(this.getEncapsulator().gameEnvironment(), new BulletSourceInformation(thisE), thisE.getX(), thisE.getY(), thisE.getAngle()));}
}

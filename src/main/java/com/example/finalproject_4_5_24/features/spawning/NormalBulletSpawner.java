package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.bullets.BulletSourceInformation;
import com.example.finalproject_4_5_24.bullets.NormalExpirationProjectiles;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class NormalBulletSpawner extends BulletSpawner{
    NormalBulletSpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown) {super(info, spawnRateCoolDown, 40, 40, 5, 1);}
    @Override public Class<? extends DynamicGameElement> getSpawningType() {return NormalExpirationProjectiles.class;}
    @Override protected void addBullet(GameElementEncapsulator<?> thisE) {this.getEncapsulator().gameEnvironment().operator().addElement(new NormalExpirationProjectiles(this.getEncapsulator().gameEnvironment(), new BulletSourceInformation(thisE), thisE.getX(), thisE.getY(), thisE.getAngle()));}
}

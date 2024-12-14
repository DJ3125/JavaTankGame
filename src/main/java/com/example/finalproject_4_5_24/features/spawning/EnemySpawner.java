package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.Enemies;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class EnemySpawner extends SingleSpawner{
    EnemySpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown) {super(info, spawnRateCoolDown);}

    @Override public void spawn() {
        if(this.canSpawn()){
            this.getTimer().performBehavior();
            this.getEncapsulator().gameEnvironment().operator().addElement(new Enemies(this.getEncapsulator().gameEnvironment(), this.getEncapsulator().getX(), this.getEncapsulator().getY()));
        }
    }

    @Override public Class<? extends DynamicGameElement> getSpawningType() {return Enemies.class;}
}

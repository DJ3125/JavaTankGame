package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.timers.ResettableTimer;
import javafx.scene.input.KeyCode;

final class PlayerBulletSpawner extends MultipleSpawner{
    PlayerBulletSpawner(DynamicGameElement.FeatureInitializationInfo info) {super(info, new FlameBulletSpawner(info, 10), new NormalBulletSpawner(info, 1));}

    @Override protected void updateOnTurn() {
        if(this.switchTimer.benchMarkReached()){
            if(this.getEncapsulator().gameEnvironment().inputs().getValueOfKeyCode(KeyCode.S)){
                this.changeSelectedSpawner((byte)1);
                this.switchTimer.performBehavior();
            }else if(this.getEncapsulator().gameEnvironment().inputs().getValueOfKeyCode(KeyCode.A)){
                this.changeSelectedSpawner((byte)-1);
                this.switchTimer.performBehavior();
            }
        }
        if(this.getEncapsulator().gameEnvironment().inputs().getValueOfKeyCode(KeyCode.S) && (this.switchTimer.performBehavior())){this.changeSelectedSpawner((byte)1);}
        this.switchTimer.incrementTimer();
        super.updateOnTurn();
    }

    private final ResettableTimer switchTimer = new ResettableTimer(200);
}

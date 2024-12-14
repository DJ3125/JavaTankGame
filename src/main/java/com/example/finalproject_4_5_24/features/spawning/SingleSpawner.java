package com.example.finalproject_4_5_24.features.spawning;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.timers.ResettableTimer;

public abstract sealed class SingleSpawner extends Spawner permits BulletSpawner, EnemySpawner {
    protected SingleSpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown, int maxAmmo, int currentAmmo, int reloadRate, int reloadIncrement){
        super(info);
        if(spawnRateCoolDown <= 0){throw new IllegalArgumentException("spawnRateCoolDown cannot be <= 0");}
//        if(spawningType == null){throw new IllegalArgumentException("spawning type cannot be null");}
        this.spawnRateCoolDown = this.normalSpawnRateCoolDown = spawnRateCoolDown;
        this.coolDown = new ResettableTimer(spawnRateCoolDown);
//        this.usage = usage;
        this.requiresAmmo = true;
        this.currentAmountOfAmmo = currentAmmo;
        this.maxAmountOfAmmo = maxAmmo;
        this.reloadRate = reloadRate;
        this.reloadIncrement = reloadIncrement;
        this.reloadTimer = new ResettableTimer(this.reloadRate);
//        this.spawningType = spawningType;
    }
    protected SingleSpawner(DynamicGameElement.FeatureInitializationInfo info, int spawnRateCoolDown){
        super(info);
        if(spawnRateCoolDown <= 0){throw new IllegalArgumentException("spawnRateCoolDown cannot be <= 0");}
//        if(spawningType == null){throw new IllegalArgumentException("spawning type cannot be null");}
        this.spawnRateCoolDown = this.normalSpawnRateCoolDown = spawnRateCoolDown;
        this.coolDown = new ResettableTimer(spawnRateCoolDown);
        this.requiresAmmo = false;
        this.maxAmountOfAmmo = Integer.MAX_VALUE;
        this.currentAmountOfAmmo = Integer.MAX_VALUE;
        this.reloadRate = Integer.MAX_VALUE;
        this.reloadTimer = new ResettableTimer(Integer.MAX_VALUE);
        this.reloadIncrement = 0;
//        this.usage = usage;
    }

    @Override public final int getReloadRate() {return this.reloadRate;}

    @Override protected void updateOnTick() {}

    @Override public int reloadIncrement() {return this.reloadIncrement;}

    @Override protected void updateOnChange() {}

    @Override protected void updateOnTurn() {
        this.coolDown.incrementTimer();
    }

    public abstract void spawn();
    protected final void setSpawnRate(int newSpawnRate){this.spawnRateCoolDown = newSpawnRate;}
    protected final ResettableTimer getTimer(){return this.coolDown;}

    @Override public final int getMaxCapacity() {return this.maxAmountOfAmmo;}
    @Override public boolean requiresAmmo() {return this.requiresAmmo;}
    @Override public final int getCurrentCapacity() {return this.currentAmountOfAmmo;}

    @Override public final boolean canSpawn() {return this.coolDown.benchMarkReached();}
    @Override public final int getSpawnRateCoolDown() {return this.spawnRateCoolDown;}
    @Override public final int getNormalSpawnRateCoolDown() {return this.normalSpawnRateCoolDown;}

    @Override public int reload(int amountGiven){
        if(this.reloadTimer.benchMarkReached()){
            this.reloadTimer.performBehavior();
            if(amountGiven < 0){throw new IllegalArgumentException("Amount Given cannot be less than 0. amountGiven = " + amountGiven);}
            if(this.currentAmountOfAmmo == this.maxAmountOfAmmo || !this.requiresAmmo){return amountGiven;}
            int amountTaken = Math.min(Math.min(amountGiven, this.reloadIncrement) + this.currentAmountOfAmmo, this.maxAmountOfAmmo) - this.currentAmountOfAmmo;
            this.currentAmountOfAmmo += amountTaken;
            return amountGiven - amountTaken;
        }else{
            this.reloadTimer.incrementTimer();
            return amountGiven;
        }
    }

    protected final boolean useAmmo(){
        if(this.requiresAmmo && this.currentAmountOfAmmo > 0){
            this.currentAmountOfAmmo--;
            return true;
        }else{return false;}
    }

    private int spawnRateCoolDown;
    private final int normalSpawnRateCoolDown;
    private final ResettableTimer coolDown;

    private final int maxAmountOfAmmo;
    private int currentAmountOfAmmo;
    private final boolean requiresAmmo;
    private int reloadRate;
    private int reloadIncrement;
    private final ResettableTimer reloadTimer;

//    private final AmmoUsage usage;

//    protected static final class AmmoUsage {
//        AmmoUsage(){
//            this.maxAmountOfAmmo = Integer.MAX_VALUE;
//            this.currentAmountOfAmmo = Integer.MAX_VALUE;
//            this.requiresAmmo = false;
//        }
//        AmmoUsage(int maxValue, int initialAmount){
//            if(maxValue <= 0 || initialAmount < 0){throw new IllegalArgumentException("Max Value Cannot be less than 0. Same with initial amount");}
//            this.maxAmountOfAmmo = maxValue;
//            this.currentAmountOfAmmo = Math.min(initialAmount, maxValue);
//            this.requiresAmmo = true;
//        }
//
//        int reload(int amountGiven){
//            if(this.requiresAmmo){
//                this.currentAmountOfAmmo += amountGiven;
//                if(this.currentAmountOfAmmo > this.maxAmountOfAmmo){
//                    int amountToRemove = this.currentAmountOfAmmo - this.maxAmountOfAmmo;
//                    this.currentAmountOfAmmo -= amountToRemove;
//                    return amountToRemove;
//                }else{return 0;}
//            }else{return amountGiven;}
//        }
//        boolean useAmmo(){
//            if(this.requiresAmmo && this.currentAmountOfAmmo > 0){
//                this.currentAmountOfAmmo--;
//                return true;
//            }else{return false;}
//        }
//        int getCurrentAmountOfAmmo(){return this.currentAmountOfAmmo;}
//        boolean needsAmmo(){return this.requiresAmmo;}
//        boolean isEmpty(){return !this.requiresAmmo && this.currentAmountOfAmmo <= 0;}
//        private final int maxAmountOfAmmo;
//        private int currentAmountOfAmmo;
//        private final boolean requiresAmmo;
//    }
}

package com.example.finalproject_4_5_24.timers;

public final class SpawnTimer extends TimeCounter{
    public SpawnTimer(int spawnRate){
        super();
        this.spawnRate = spawnRate;
    }
    @Override public boolean benchMarkReached() {return this.getCurrentTime() % this.spawnRate == 0;}
    private final int spawnRate;
}

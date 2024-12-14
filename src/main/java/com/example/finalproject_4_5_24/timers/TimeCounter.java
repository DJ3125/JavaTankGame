package com.example.finalproject_4_5_24.timers;

abstract sealed public class TimeCounter permits ActivationTimer, ResetingTimer, ResettableTimer, SpawnTimer {
    protected TimeCounter(){this.currentTime = 0;}
    protected final int getCurrentTime() {return this.currentTime;}
    public boolean performBehavior(){return this.benchMarkReached();}
    abstract public boolean benchMarkReached();
    public final void incrementTimer(){this.currentTime++;}
    protected final void reset(){this.currentTime = 0;}
    private int currentTime;
}

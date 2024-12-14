package com.example.finalproject_4_5_24.timers;

public final class ActivationTimer extends TimeCounter{
    public ActivationTimer(int delay){
        super();
        this.delay = delay;
    }
    @Override public boolean benchMarkReached() {return this.getCurrentTime() > this.delay;}
    private final int delay;
}

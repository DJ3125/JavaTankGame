package com.example.finalproject_4_5_24.timers;

public final class ResettableTimer extends TimeCounter{
    public ResettableTimer(int benchMark){
        super();
        if(benchMark <= 0){throw new IllegalArgumentException("the benchmark cannot be <= 0");}
        this.benchMark = benchMark;
    }

    @Override public boolean performBehavior() {
        if(this.benchMarkReached()){
            this.reset();
            this.onStart = false;
            return true;
        }else{return false;}
    }

    public boolean benchMarkReached(){return this.onStart || this.getCurrentTime() >= this.benchMark;}

    public void setBenchMark(int benchMark) {this.benchMark = benchMark;}
    private int benchMark;
    private boolean onStart = true;
}

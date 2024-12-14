package com.example.finalproject_4_5_24.timers;

public final class ResetingTimer extends TimeCounter{
    public ResetingTimer(int benchMark){
        super();
        this.benchMark = benchMark;
    }
    @Override public boolean benchMarkReached() {return this.getCurrentTime() > this.benchMark;}
    @Override public boolean performBehavior() {
        boolean result = this.benchMarkReached();
        this.reset();
        return result;
    }
    public void resetTimer(){this.reset();}
    public void setBenchMark(int benchMark) {this.benchMark = benchMark;}
    private int benchMark;
}

package com.example.finalproject_4_5_24.controllerAndInput.levels.layers;

import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes.BlankTransition;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes.NonNullTransition;

abstract public sealed class Transitions extends LevelLayers<Scenes> permits BlankTransition, NonNullTransition {
    protected Transitions(Scenes currentScene){super(currentScene);}
    protected final void incrementTickCount(){this.tickCount++;}
    protected final long getTickCount(){return this.tickCount;}
    public boolean isFinished(){return true;}
    protected int getIterations(){return 0;}
    private long tickCount = 0;
}

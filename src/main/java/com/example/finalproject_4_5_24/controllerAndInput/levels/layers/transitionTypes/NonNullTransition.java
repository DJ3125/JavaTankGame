package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes;

import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.Transitions;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

abstract public sealed class NonNullTransition extends Transitions permits CircularTransition, FadeTransition {
    protected NonNullTransition(Scenes currentScene, boolean transitionIn, Color color, int duration){
        super(currentScene);
        this.fadeColor = color;
        if(duration <= 0){throw new RuntimeException("Duration cannot be <= 0");}
        this.iterations = duration;
        this.transitionIn = transitionIn;
    }
    @Override protected final void update() {this.incrementTickCount();}
    @Override
    protected void draw(GraphicsContext ctx) {
        ctx.save();
        this.performDraw(ctx);
        ctx.restore();
    }

    @Override public boolean isFinished() {return this.iterations < this.getTickCount();}

    @Override protected boolean canUpdate(boolean inputChain) {return inputChain && this.isFinished();}

    @Override protected final PlayerInputDecorator modifyEvent(PlayerInputDecorator playerInputEvent) {
        if(this.isFinished()){return playerInputEvent;}
        else{return PlayerInputDecorator.getNullInstance(playerInputEvent);}
    }

    protected final long getCount(){
        if(this.transitionIn){return Math.max(this.iterations - this.getTickCount(), 0);}
        else{return this.getTickCount();}
    }
    protected final Color getFadeColor() {return this.fadeColor;}
    @Override protected final int getIterations(){return this.iterations;}
    abstract protected void performDraw(GraphicsContext ctx);
    private final Color fadeColor;
    private final int iterations;
    private final boolean transitionIn;
}

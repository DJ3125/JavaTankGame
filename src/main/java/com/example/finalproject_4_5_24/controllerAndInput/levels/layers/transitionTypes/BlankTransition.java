package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes;

import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.Transitions;
import javafx.scene.canvas.GraphicsContext;

public final class BlankTransition extends Transitions {
    public BlankTransition(Scenes currentScene) {super(currentScene);}

    //    @Override public boolean update() {return true;}
    @Override
    protected void draw(GraphicsContext ctx) {}
    @Override
    protected void update() {}
    @Override
    protected boolean canUpdate(boolean inputChain) {return inputChain;}
    @Override
    protected PlayerInputDecorator modifyEvent(PlayerInputDecorator playerInputEvent) {return playerInputEvent;}
}

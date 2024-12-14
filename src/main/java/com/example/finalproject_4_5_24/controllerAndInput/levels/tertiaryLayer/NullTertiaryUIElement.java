package com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer;

import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.TertiaryLayerUIElements;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Consumer;

public final class NullTertiaryUIElement extends TertiaryLayerUIElements {
    public NullTertiaryUIElement(Level level, Consumer<TertiaryLayerUIElements> switching) {super(level, switching);}
    @Override
    protected void draw(GraphicsContext ctx) {}
    @Override
    protected void update() {}
    @Override
    protected boolean canUpdate(boolean inputChain) {return inputChain;}
    @Override
    protected PlayerInputDecorator modifyEvent(PlayerInputDecorator playerInputEvent) {return playerInputEvent;}
}

package com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;

import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import javafx.scene.canvas.GraphicsContext;

public final class NullUIElement extends UIElement<LevelLayers<?>>{
    public NullUIElement() {super(null, 0, 0, 0, 0);}
    @Override public void drawSelf(GraphicsContext ctx) {}
    @Override public void updateSelf() {}
    @Override public boolean clickedOn() {return false;}
}

package com.example.finalproject_4_5_24.controllerAndInput;

import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.InventoryViewing;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.*;
import javafx.scene.canvas.GraphicsContext;

abstract public sealed class UIElement<T extends LevelLayers<?>> permits InventoryViewing.InventoryButton, InventoryViewing.InventoryUsageOption, BulletAndAmmoDisplay, Button, HealthBar, NullUIElement, ObjectiveDisplay {
    protected UIElement(T layers, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.layer = layers;
    }

    public abstract void drawSelf(GraphicsContext ctx);
    public abstract void updateSelf();
    public final boolean mouseOver(){return Math.abs(this.layer.getInputs().getScreenMouseX() - this.x) <= this.width/2 && Math.abs(this.layer.getInputs().getScreenMouseY() - this.y) <= this.height/2;}
    public boolean clickedOn(){return this.mouseOver() && this.layer.getInputs().mouseClicked();}

    protected final float getHeight() {return this.height;}
    protected final float getX() {return this.x;}
    protected final float getY() {return this.y;}
    protected final float getWidth() {return this.width;}
    protected final T getLayer(){return this.layer;}
    private final float x, y, width, height;
    private final T layer;
}

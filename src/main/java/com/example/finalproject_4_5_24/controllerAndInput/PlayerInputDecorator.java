package com.example.finalproject_4_5_24.controllerAndInput;

import javafx.scene.input.KeyCode;

public interface PlayerInputDecorator {
    boolean getValueOfKeyCode(KeyCode keyCode);
    float getScreenMouseX();
    float getScreenMouseY();
    float getLastMouseX();
    float getLastMouseY();
    boolean mouseClicked();

    static PlayerInputDecorator getNullInstance(PlayerInputDecorator inputEvent){
        return new PlayerInputEvent(inputEvent){
            @Override public boolean getValueOfKeyCode(KeyCode keyCode) {return false;}
            @Override public float getScreenMouseX() {return this.getLayerBeneath().getLastMouseX();}
            @Override public float getScreenMouseY() {return this.getLayerBeneath().getLastMouseY();}
            @Override public float getLastMouseX() {return this.getLayerBeneath().getLastMouseX();}
            @Override public float getLastMouseY() {return this.getLayerBeneath().getLastMouseY();}
            @Override public boolean mouseClicked() {return false;}
        };
    }
}

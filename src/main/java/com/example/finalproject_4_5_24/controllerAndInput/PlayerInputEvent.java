package com.example.finalproject_4_5_24.controllerAndInput;

abstract public class PlayerInputEvent implements PlayerInputDecorator{
    public PlayerInputEvent(PlayerInputDecorator layerBeneath){this.layerBeneath = layerBeneath;}
    public final PlayerInputDecorator getLayerBeneath() {return this.layerBeneath;}
    private final PlayerInputDecorator layerBeneath;
}

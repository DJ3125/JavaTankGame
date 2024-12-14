package com.example.finalproject_4_5_24.controllerAndInput;

import javafx.scene.canvas.GraphicsContext;

abstract public class LevelLayers<T extends Scenes>{
    public LevelLayers(T currentScene){this.currentScene = currentScene;}
    protected abstract void draw(GraphicsContext ctx);
    protected boolean canUpdate(boolean inputChain){return inputChain;}
    final boolean relayUpdateChain(boolean inputChain){
        this.canUpdateThisTurn = inputChain;
        return this.canUpdate(inputChain);
    }
    final PlayerInputDecorator relayInputChain(PlayerInputDecorator decorator){
        this.inputs = decorator;
        return modifyEvent(decorator);
    }
    public void addUIElement(UIElement<?> element){}
    final void updateLayer(){if(this.canUpdateThisTurn){this.update();}}
    protected abstract void update();
    protected PlayerInputDecorator modifyEvent(PlayerInputDecorator playerInputEvent){return playerInputEvent;}
    public final T getCurrentScene(){return this.currentScene;}
    public final PlayerInputDecorator getInputs() {return this.inputs;}
    private final T currentScene;
    private boolean canUpdateThisTurn;
    private PlayerInputDecorator inputs;
}

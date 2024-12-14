package com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.UILayer;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.Button;
import javafx.scene.canvas.GraphicsContext;

public final class TitleScreen extends Scenes{
    public TitleScreen(){
        this.uiLayer.addUIElement(new Button("Play", false, this.uiLayer, GameController.canvasWidth()/2, GameController.canvasHeight()/4 * 3, 50, 50, button -> {
            this.switchScenes();
        }));
    }
    @Override public void switchScenes() {this.requestSceneSwitch(new LevelOne());}

    @Override protected void updateSelf(PlayerInputDecorator inputs) {
        this.updateLayers(this.uiLayer);
    }

    @Override protected void drawSelf(GraphicsContext ctx) {
        this.drawLayers(ctx, this.uiLayer);
    }

    private final UILayer uiLayer = new UILayer(this);
}
